package fr.ubordeaux.ddd.tests.utils;

import com.tngtech.archunit.core.domain.JavaClass;
import com.tngtech.archunit.core.domain.JavaClasses;
import com.tngtech.archunit.core.importer.ClassFileImporter;

import fr.ubordeaux.ddd.rules.utils.PackageUtils;

import java.nio.file.Path;
import java.nio.file.Paths;

public class ArchUtils {
	final private static String JUNIT_ASSERT_PACKAGE_NAME = "org.junit.Assert";
	final public static String NO_JUNIT_ASSERT_DESCRIPTION = "not use Junit assertions";

	private ArchUtils() {
		throw new UnsupportedOperationException();
	}

	public static JavaClasses importAllClassesInPackage(String path, String classFolder) {
		Path classesPath = Paths.get(path + classFolder);
		if (classesPath.toFile().exists()) {
			return new ClassFileImporter().importPath(classesPath);
		}
		return new ClassFileImporter().importPath(Paths.get(path));
	}

	public static boolean isJunitAssert(JavaClass javaClass) {
		return (JUNIT_ASSERT_PACKAGE_NAME).equals(new StringBuilder().append(javaClass.getPackageName()).append(PackageUtils.SEPARATOR).append(javaClass.getSimpleName()).toString());
	}
}