package fr.ubordeaux.ddd.rules.utils;

import java.lang.annotation.Annotation;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import com.tngtech.archunit.core.domain.JavaClass;
import com.tngtech.archunit.core.domain.JavaClasses;
import com.tngtech.archunit.core.importer.ClassFileImporter;

import fr.ubordeaux.ddd.annotations.packages.Anticorruption;
import fr.ubordeaux.ddd.annotations.packages.Application;
import fr.ubordeaux.ddd.annotations.packages.Domain;
import fr.ubordeaux.ddd.annotations.packages.Infrastructure;
import fr.ubordeaux.ddd.annotations.packages.Presentation;

public class PackageUtils {
	final public static Class<?>[] ANNOTATIONS = {Anticorruption.class, Presentation.class, Application.class, Domain.class, Infrastructure.class};
	final public static String PACKAGE_INFO = "package-info";
	final public static String SUB_SYMBOL = "..";
	final public static String SEPARATOR = ".";

	final protected static Set<JavaClass> packageInfoClasses = new HashSet<>();

	final private static Set<Package> packages = new HashSet<>();
	final private static Map<Class<? extends Annotation>, String[]> annotatedPackages = new HashMap<>();

	protected static Package loadPackage(String name, String className) {
		ClassLoader classLoader = ClassLoader.getSystemClassLoader();
		try {
			classLoader.loadClass(name + SEPARATOR + className);
			return classLoader.getDefinedPackage(name);
		} catch (ClassNotFoundException e) {}
		return null;
	}

	public static void importAllPackageInfoClasses(JavaClasses classes) {
		for (JavaClass importedClass : classes) {
			if (importedClass.getSimpleName().compareTo("package-info") == 0) {
				PackageUtils.packageInfoClasses.add(importedClass);
			}
		}
	}

	public static void clearAllPackageInfoClasses() {
		PackageUtils.packageInfoClasses.clear();
	}

	private static void importAllPackages() {
		JavaClasses classes = new ClassFileImporter().importPackages(SUB_SYMBOL);
		for (JavaClass importedClass : classes) {
			packages.add(loadPackage(importedClass.getPackageName(), PACKAGE_INFO));
		}
	}

	private static String[] getPackagesFromPackageInfoClassesAnnotatedWith(Class<? extends Annotation> annotation) {
		Set<String> names = new HashSet<>();
		for (JavaClass packageInfo : packageInfoClasses) {
			if (packageInfo.isAnnotatedWith(annotation)) {
				names.add(packageInfo.getPackageName() + SUB_SYMBOL);
			}
		}
		annotatedPackages.put(annotation, Arrays.copyOf(names.toArray(), names.size(), String[].class));
		return annotatedPackages.get(annotation);
	}

	private static String[] getPackagesAnnotatedWith(Class<? extends Annotation> annotation) {
		if (packages.isEmpty()) {
			importAllPackages();
		}
		Set<String> names = new HashSet<>();
		for (Package annotatedPackage : packages) {
			if (annotatedPackage != null && annotatedPackage.isAnnotationPresent(annotation)) {
				names.add(annotatedPackage.getName() + SUB_SYMBOL);
			}
		}
		annotatedPackages.put(annotation, Arrays.copyOf(names.toArray(), names.size(), String[].class));
		return annotatedPackages.get(annotation);
	}

	public static String[] getAllPackagesAnnotatedWith(Class<? extends Annotation> annotation) {
		if (annotatedPackages.containsKey(annotation)) {
			return annotatedPackages.get(annotation);
		}
		if (!packageInfoClasses.isEmpty()) {
			return getPackagesFromPackageInfoClassesAnnotatedWith(annotation);
		}
		return getPackagesAnnotatedWith(annotation);
	}
}