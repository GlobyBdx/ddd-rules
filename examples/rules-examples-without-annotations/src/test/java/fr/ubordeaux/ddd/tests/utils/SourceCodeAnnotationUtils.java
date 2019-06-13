package fr.ubordeaux.ddd.tests.utils;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;

import fr.ubordeaux.ddd.rules.utils.PackageUtils;

public class SourceCodeAnnotationUtils {
	final public static String ROOT = "src/main/java/";
	final public static String EXTENSION = ".java";
	final public static String SEPARATOR = "/";

	private static String computePackageInfoFilePath(String name) {
		return ROOT + name.replace(PackageUtils.SEPARATOR, SEPARATOR) + SEPARATOR + PackageUtils.PACKAGE_INFO + EXTENSION;
	}

	public static void createPackageInfoFiles() {
		for (String annotation : PackageUtils.annotatedPackages.keySet()) {
			for (String annotatedPackage : PackageUtils.annotatedPackages.get(annotation)) {
				try (Writer writer = new FileWriter(computePackageInfoFilePath(annotatedPackage))) {
					writer.write("package " + annotatedPackage + ";");
					writer.flush();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

	public static void deletePackageInfoFiles() {
		for (String annotation : PackageUtils.annotatedPackages.keySet()) {
			for (String annotatedPackage : PackageUtils.annotatedPackages.get(annotation)) {
				new File(computePackageInfoFilePath(annotatedPackage)).delete();
			}
		}
	}
}