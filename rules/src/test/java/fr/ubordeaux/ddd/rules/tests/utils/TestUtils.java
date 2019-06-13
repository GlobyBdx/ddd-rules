package fr.ubordeaux.ddd.rules.tests.utils;

import java.util.Set;

import com.tngtech.archunit.core.domain.JavaClasses;
import com.tngtech.archunit.core.importer.ClassFileImporter;

public class TestUtils {
	public static JavaClasses computeClasses(Set<Class<?>> classes) {
		return new ClassFileImporter().importClasses(classes);
	}

	public static JavaClasses computeClassesExcept(Set<Class<?>> classes, Set<Class<?>> exceptions) {
		classes.removeAll(exceptions);
		JavaClasses computedClasses = computeClasses(classes);
		classes.addAll(exceptions);
		return computedClasses;
	}
}