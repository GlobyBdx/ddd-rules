package fr.ubordeaux.ddd.tests.utils;

import java.lang.annotation.Annotation;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import com.tngtech.archunit.core.domain.JavaAnnotation;
import com.tngtech.archunit.core.domain.JavaClass;
import com.tngtech.archunit.core.domain.JavaClasses;
import com.tngtech.archunit.core.domain.JavaPackage;
import com.tngtech.archunit.core.importer.ClassFileImporter;

import fr.ubordeaux.ddd.rules.utils.PackageUtils;

public class ArchitectureExportUtils {
	final static public JavaClasses classes = new ClassFileImporter().importPackages("fr.ubordeaux.ddd.example..");

	public static Map<JavaPackage, Set<JavaAnnotation>> importAllPackages() {
		Map<JavaPackage, Set<JavaAnnotation>> packages = new HashMap<>();
		for (JavaClass importedClass : classes) {
			if (!packages.containsKey(importedClass.getPackage())) {
				packages.put(importedClass.getPackage(), new HashSet<>());
			}
			try {
				JavaClass packageInfo = importedClass.getPackage().getClassWithSimpleName(PackageUtils.PACKAGE_INFO);
				for (JavaAnnotation annotation : packageInfo.getAnnotations()) {
					if (Annotation.class.isAssignableFrom(annotation.getRawType().reflect())) {
						packages.get(importedClass.getPackage()).add(annotation);
					}
				}
			} catch (IllegalArgumentException e) {}
		}
		return packages;
	}
}