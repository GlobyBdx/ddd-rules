package fr.ubordeaux.ddd.tests.utils;

import java.util.AbstractMap;
import java.util.HashSet;
import java.util.Map;

import com.tngtech.archunit.core.domain.JavaClass;
import com.tngtech.archunit.core.domain.JavaClasses;
import com.tngtech.archunit.core.domain.JavaField;
import com.tngtech.archunit.core.domain.JavaMethod;

import fr.ubordeaux.ddd.rules.utils.ClassUtils;
import fr.ubordeaux.ddd.rules.utils.FieldUtils;
import fr.ubordeaux.ddd.rules.utils.MethodUtils;
import fr.ubordeaux.ddd.rules.utils.PackageUtils;

public class ArchitectureImportUtils {
	final private static String ITEM_SEPARATOR = ", ";

	protected static void importPackagesFromPackageInfoClasses() {
		for (String annotation : PackageUtils.ANNOTATIONS) {
			for (String packageInfo : ClassUtils.annotatedClasses.get(annotation)) {
				if (!PackageUtils.annotatedPackages.containsKey(annotation)) {
					PackageUtils.annotatedPackages.put(annotation, new HashSet<>());
				}
				PackageUtils.annotatedPackages.get(annotation).add(packageInfo.substring(0, packageInfo.lastIndexOf(PackageUtils.SEPARATOR)));
			}
		}
	}

	public static void importPackages() {
		// ArchitectureJsonImportUtils.importJSONPackages();
		ArchitectureXmlImportUtils.importXMLPackages();
	}

	public static void clearPackages() {
		PackageUtils.annotatedPackages.clear();
	}

	public static void importArchitecture() {
		// ArchitectureJsonImportUtils.importJSONPackagesArchitecture();
		// ArchitectureJsonImportUtils.importJSONClassesArchitecture();
		// ArchitectureJsonImportUtils.importJSONMinimalArchitecture();
		// ArchitectureXmlImportUtils.importXMLPackagesArchitecture();
		// ArchitectureXmlImportUtils.importXMLClassesArchitecture();
		ArchitectureXmlImportUtils.importXMLMinimalArchitecture();
	}

	public static void clearArchitecture() {
		PackageUtils.annotatedPackages.clear();
		ClassUtils.annotatedClasses.clear();
		FieldUtils.annotatedFields.clear();
		MethodUtils.annotatedMethods.clear();
	}

	public static Map.Entry<Double, String> compareWithSourceCodeClasses(JavaClasses classes) {
		double searched = 0, found = 0;
		StringBuilder stringBuilder = new StringBuilder();
		for (String annotation : ClassUtils.annotatedClasses.keySet()) {
			for (String annotatedClass : ClassUtils.annotatedClasses.get(annotation)) {
				if (!annotatedClass.endsWith(PackageUtils.PACKAGE_INFO)) {
					searched++;
					found++;
					if (!classes.contain(annotatedClass)) {
						stringBuilder.append(annotatedClass + ITEM_SEPARATOR);
						found--;
					}
				}
			}
		}
		String notFound = (stringBuilder.length() > ITEM_SEPARATOR.length()) ? stringBuilder.substring(0, stringBuilder.length() - ITEM_SEPARATOR.length()) : null;
		return new AbstractMap.SimpleEntry<Double, String>(found / searched, notFound);
	}

	public static Map.Entry<Double, String> compareWithSourceCodeFields(JavaClasses classes) {
		double searched = 0, found = 0;
		StringBuilder stringBuilder = new StringBuilder();
		for (String annotation : FieldUtils.annotatedFields.keySet()) {
			for (String annotatedField : FieldUtils.annotatedFields.get(annotation)) {
				searched++;
				found++;
				boolean check = false;
				for (JavaClass temporaryClass : classes) {
					for (JavaField field : temporaryClass.getFields()) {
						if (field.getFullName().compareTo(annotatedField) == 0) {
							check = true;
							break;
						}
					}
					if (check) {
						break;
					}
				}
				if (!check) {
					stringBuilder.append(annotatedField + ITEM_SEPARATOR);
					found--;
				}
			}
		}
		String notFound = (stringBuilder.length() > ITEM_SEPARATOR.length()) ? stringBuilder.substring(0, stringBuilder.length() - ITEM_SEPARATOR.length()) : null;
		return new AbstractMap.SimpleEntry<Double, String>(found / searched, notFound);
	}

	public static Map.Entry<Double, String> compareWithSourceCodeMethods(JavaClasses classes) {
		double searched = 0, found = 0;
		StringBuilder stringBuilder = new StringBuilder();
		for (String annotation : MethodUtils.annotatedMethods.keySet()) {
			for (String annotatedMethod : MethodUtils.annotatedMethods.get(annotation)) {
				searched++;
				found++;
				boolean check = false;
				for (JavaClass temporaryClass : classes) {
					for (JavaMethod method : temporaryClass.getMethods()) {
						if (method.getFullName().compareTo(annotatedMethod) == 0) {
							check = true;
							break;
						}
					}
					if (check) {
						break;
					}
				}
				if (!check) {
					stringBuilder.append(annotatedMethod + ITEM_SEPARATOR);
					found--;
				}
			}
		}
		String notFound = (stringBuilder.length() > ITEM_SEPARATOR.length()) ? stringBuilder.substring(0, stringBuilder.length() - ITEM_SEPARATOR.length()) : null;
		return new AbstractMap.SimpleEntry<Double, String>(found / searched, notFound);
	}
}