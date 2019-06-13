package fr.ubordeaux.ddd.rules.utils;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import fr.ubordeaux.ddd.tests.utils.ArchitectureImportUtils;

public class PackageUtils {
	final public static String[] ANNOTATIONS = {"Anticorruption", "Presentation", "Application", "Domain", "Infrastructure"};
	final public static String PACKAGE_INFO = "package-info";
	final public static String SUB_SYMBOL = "..";
	final public static String SEPARATOR = ".";

	final public static Map<String, Set<String>> annotatedPackages = new HashMap<>();

	private static String[] getPackagesAnnotatedWith(String annotation, boolean sub) {
		if (annotatedPackages.isEmpty()) {
			ArchitectureImportUtils.importArchitecture();
		}
		if (!sub) {
			return Arrays.copyOf(annotatedPackages.get(annotation).toArray(), annotatedPackages.get(annotation).size(), String[].class);
		}
		Set<String> names = new HashSet<>();
		for (String annotatedPackage : annotatedPackages.get(annotation)) {
			names.add(annotatedPackage + SUB_SYMBOL);
		}
		return Arrays.copyOf(names.toArray(), names.size(), String[].class);
	}

	protected static String[] getAllPackagesAnnotatedWith(String annotation) {
		return getPackagesAnnotatedWith(annotation, false);
	}

	public static String[] getAllSubPackagesAnnotatedWith(String annotation) {
		return getPackagesAnnotatedWith(annotation, true);
	}
}