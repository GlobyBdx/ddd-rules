package fr.ubordeaux.ddd.tests;

import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.lang.annotation.Annotation;
import java.util.Arrays;
import java.util.Map;
import java.util.Set;

import com.google.common.collect.Sets;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.tngtech.archunit.core.domain.JavaAnnotation;
import com.tngtech.archunit.core.domain.JavaClass;
import com.tngtech.archunit.core.domain.JavaClassList;
import com.tngtech.archunit.core.domain.JavaField;
import com.tngtech.archunit.core.domain.JavaMethod;
import com.tngtech.archunit.core.domain.JavaModifier;
import com.tngtech.archunit.core.domain.JavaPackage;

import fr.ubordeaux.ddd.tests.utils.ArchitectureExportUtils;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.junit.Test;

public class ArchitectureJsonExport {
	@SuppressWarnings("unchecked")
	private JSONArray exportAnnotationsAsJSON(Set<JavaAnnotation> annotations) {
		JSONArray jsonAnnotations = new JSONArray();
		for (JavaAnnotation annotation : annotations) {
			if (Annotation.class.isAssignableFrom(annotation.getRawType().reflect())) {
				jsonAnnotations.add(annotation.getRawType().getSimpleName());
			}
		}
		return jsonAnnotations;
	}

	@SuppressWarnings("unchecked")
	private JSONArray exportModifiersAsJSON(Set<JavaModifier> modifiers) {
		JSONArray jsonModifiers = new JSONArray();
		for (JavaModifier modifier : modifiers) {
			jsonModifiers.add(modifier.name());
		}
		return jsonModifiers;
	}

	@SuppressWarnings("unchecked")
	private JSONArray exportFieldsAsJSON(Set<JavaField> fields, boolean minimal) {
		JSONArray jsonFields = new JSONArray();
		for (JavaField field : fields) {
			JSONObject jsonField = new JSONObject();
			jsonField.put("name", field.getFullName());
			jsonField.put("annotations", exportAnnotationsAsJSON(field.getAnnotations()));
			if (!minimal) {
				jsonField.put("modifiers", exportModifiersAsJSON(field.getModifiers()));
				jsonField.put("type", field.getRawType().getFullName());
			}
			jsonFields.add(jsonField);
		}
		return jsonFields;
	}

	@SuppressWarnings("unchecked")
	private JSONArray exportParameterTypesAsJSON(JavaClassList parameterTypes) {
		JSONArray jsonParameterTypes = new JSONArray();
		for (JavaClass parameterType : parameterTypes) {
			jsonParameterTypes.add(parameterType.getFullName());
		}
		return jsonParameterTypes;
	}

	@SuppressWarnings("unchecked")
	private JSONArray exportMethodsAsJSON(Set<JavaMethod> methods, boolean minimal) {
		JSONArray jsonMethods = new JSONArray();
		for (JavaMethod method : methods) {
			JSONObject jsonMethod = new JSONObject();
			jsonMethod.put("name", method.getFullName());
			jsonMethod.put("annotations", exportAnnotationsAsJSON(method.getAnnotations()));
			if (!minimal) {
				jsonMethod.put("modifiers", exportModifiersAsJSON(method.getModifiers()));
				jsonMethod.put("parameter_types", exportParameterTypesAsJSON(method.getRawParameterTypes()));
				jsonMethod.put("return_type", method.getRawReturnType().getFullName());
			}
			jsonMethods.add(jsonMethod);
		}
		return jsonMethods;
	}

	@SuppressWarnings("unchecked")
	private JSONArray exportClassesAsJSON(Set<JavaClass> classes, boolean minimal) {
		JSONArray jsonClasses = new JSONArray();
		for (JavaClass exportedClass : classes) {
			JSONObject jsonClass = new JSONObject();
			jsonClass.put("name", exportedClass.getFullName());
			jsonClass.put("annotations", exportAnnotationsAsJSON(exportedClass.getAnnotations()));
			jsonClass.put("fields", exportFieldsAsJSON(exportedClass.getFields(), minimal));
			jsonClass.put("methods", exportMethodsAsJSON(exportedClass.getMethods(), minimal));
			if (!minimal) {
				jsonClass.put("modifiers", exportModifiersAsJSON(exportedClass.getModifiers()));
			}
			jsonClasses.add(jsonClass);
		}
		return jsonClasses;
	}

	@SuppressWarnings("unchecked")
	private JSONArray exportPackagesAsJSON(Map<JavaPackage, Set<JavaAnnotation>> packages, boolean minimal) {
		JSONArray jsonPackages = new JSONArray();
		for (JavaPackage exportedPackage : packages.keySet()) {
			JSONObject jsonPackage = new JSONObject();
			jsonPackage.put("name", exportedPackage.getName());
			jsonPackage.put("annotations", exportAnnotationsAsJSON(packages.get(exportedPackage)));
			if (!minimal) {
				jsonPackage.put("classes", exportClassesAsJSON(exportedPackage.getClasses(), minimal));
			}
			jsonPackages.add(jsonPackage);
		}
		return jsonPackages;
	}

	private void exportAsJSON(String name, JSONObject jsonObject) {
		try (Writer writer = new FileWriter(name)) {
			JsonParser jsonParser = new JsonParser();
			JsonElement jsonElement = jsonParser.parse(jsonObject.toJSONString());
			Gson gson = new GsonBuilder().setPrettyPrinting().create();
			writer.write(gson.toJson(jsonElement));
			writer.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@SuppressWarnings("unchecked")
	@Test
	public void exportPackagesAsJSON() {
		Map<JavaPackage, Set<JavaAnnotation>> packages = ArchitectureExportUtils.importAllPackages();
		JSONObject jsonPackages = new JSONObject();
		jsonPackages.put("packages", exportPackagesAsJSON(packages, true));
		exportAsJSON("packages.json", jsonPackages);
	}

	@SuppressWarnings("unchecked")
	@Test
	public void exportArchitectureFromPackagesAsJSON() {
		Map<JavaPackage, Set<JavaAnnotation>> packages = ArchitectureExportUtils.importAllPackages();
		JSONObject jsonPackagesArchitecture = new JSONObject();
		jsonPackagesArchitecture.put("packages", exportPackagesAsJSON(packages, false));
		exportAsJSON("packages-architecture.json", jsonPackagesArchitecture);
	}

	@SuppressWarnings("unchecked")
	@Test
	public void exportArchitectureFromClassesAsJSON() {
		JSONObject jsonClassesArchitecture = new JSONObject();
		jsonClassesArchitecture.put("classes", exportClassesAsJSON(Sets.newHashSet(Arrays.copyOf(ArchitectureExportUtils.classes.toArray(), ArchitectureExportUtils.classes.size(), JavaClass[].class)), false));
		exportAsJSON("classes-architecture.json", jsonClassesArchitecture);
	}

	@SuppressWarnings("unchecked")
	@Test
	public void exportMinimalArchitectureAsJSON() {
		JSONObject jsonMinimalArchitecture = new JSONObject();
		jsonMinimalArchitecture.put("classes", exportClassesAsJSON(Sets.newHashSet(Arrays.copyOf(ArchitectureExportUtils.classes.toArray(), ArchitectureExportUtils.classes.size(), JavaClass[].class)), true));
		exportAsJSON("minimal-architecture.json", jsonMinimalArchitecture);
	}
}