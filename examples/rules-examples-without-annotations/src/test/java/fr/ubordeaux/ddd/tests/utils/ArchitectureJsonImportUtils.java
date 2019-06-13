package fr.ubordeaux.ddd.tests.utils;

import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import fr.ubordeaux.ddd.rules.utils.ClassUtils;
import fr.ubordeaux.ddd.rules.utils.FieldUtils;
import fr.ubordeaux.ddd.rules.utils.MethodUtils;
import fr.ubordeaux.ddd.rules.utils.PackageUtils;

public class ArchitectureJsonImportUtils {
	@SuppressWarnings("unchecked")
	private static void importAnnotations(String name, JSONArray jsonAnnotations, Map<String, Set<String>> annotatedMap) {
		Iterator<String> jsonAnnotationsIterator = jsonAnnotations.iterator();
		while (jsonAnnotationsIterator.hasNext()) {
			String annotation = jsonAnnotationsIterator.next();
			if (!annotatedMap.containsKey(annotation)) {
				annotatedMap.put(annotation, new HashSet<>());
			}
			annotatedMap.get(annotation).add(name);
		}
	}

	@SuppressWarnings("unchecked")
	private static void importFields(JSONArray jsonFields) {
		Iterator<JSONObject> jsonFieldsIterator = jsonFields.iterator();
		while (jsonFieldsIterator.hasNext()) {
			JSONObject jsonField = jsonFieldsIterator.next();
			importAnnotations((String)jsonField.get("name"), (JSONArray)jsonField.get("annotations"), FieldUtils.annotatedFields);
		}
	}

	@SuppressWarnings("unchecked")
	private static void importMethods(JSONArray jsonMethods) {
		Iterator<JSONObject> jsonMethodsIterator = jsonMethods.iterator();
		while (jsonMethodsIterator.hasNext()) {
			JSONObject jsonMethod = jsonMethodsIterator.next();
			importAnnotations((String)jsonMethod.get("name"), (JSONArray)jsonMethod.get("annotations"), MethodUtils.annotatedMethods);
		}
	}

	@SuppressWarnings("unchecked")
	private static void importClasses(JSONArray jsonClasses) {
		Iterator<JSONObject> jsonClassesIterator = jsonClasses.iterator();
		while (jsonClassesIterator.hasNext()) {
			JSONObject jsonClass = jsonClassesIterator.next();
			importAnnotations((String)jsonClass.get("name"), (JSONArray)jsonClass.get("annotations"), ClassUtils.annotatedClasses);
			importFields((JSONArray)jsonClass.get("fields"));
			importMethods((JSONArray)jsonClass.get("methods"));
		}
	}

	@SuppressWarnings("unchecked")
	private static void importPackages(JSONArray jsonPackages, boolean minimal) {
		Iterator<JSONObject> jsonPackagesIterator = jsonPackages.iterator();
		while (jsonPackagesIterator.hasNext()) {
			JSONObject jsonPackage = jsonPackagesIterator.next();
			importAnnotations((String)jsonPackage.get("name"), (JSONArray)jsonPackage.get("annotations"), PackageUtils.annotatedPackages);
			if (!minimal) {
				importClasses((JSONArray)jsonPackage.get("classes"));
			}
		}
	}

	protected static void importJSONPackages() {
		JSONParser jsonParser = new JSONParser();
		try (Reader reader = new FileReader("../ddd-rules-examples/packages.json")) {
			importPackages((JSONArray)((JSONObject)jsonParser.parse(reader)).get("packages"), true);
		} catch (IOException | ParseException e) {
			e.printStackTrace();
		}
	}

	protected static void importJSONPackagesArchitecture() {
		JSONParser jsonParser = new JSONParser();
		try (Reader reader = new FileReader("../ddd-rules-examples/packages-architecture.json")) {
			importPackages((JSONArray)((JSONObject)jsonParser.parse(reader)).get("packages"), false);
		} catch (IOException | ParseException e) {
			e.printStackTrace();
		}
	}

	protected static void importJSONClassesArchitecture() {
		JSONParser jsonParser = new JSONParser();
		try (Reader reader = new FileReader("../ddd-rules-examples/classes-architecture.json")) {
			importClasses((JSONArray)((JSONObject)jsonParser.parse(reader)).get("classes"));
		} catch (IOException | ParseException e) {
			e.printStackTrace();
		}
		ArchitectureImportUtils.importPackagesFromPackageInfoClasses();
	}

	protected static void importJSONMinimalArchitecture() {
		JSONParser jsonParser = new JSONParser();
		try (Reader reader = new FileReader("../ddd-rules-examples/minimal-architecture.json")) {
			importClasses((JSONArray)((JSONObject)jsonParser.parse(reader)).get("classes"));
		} catch (IOException | ParseException e) {
			e.printStackTrace();
		}
		ArchitectureImportUtils.importPackagesFromPackageInfoClasses();
	}
}