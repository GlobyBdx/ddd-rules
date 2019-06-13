package fr.ubordeaux.ddd.tests;

import java.util.Map;

import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import com.tngtech.archunit.core.domain.JavaClasses;
import com.tngtech.archunit.core.importer.ClassFileImporter;

import fr.ubordeaux.ddd.tests.utils.ArchitectureImportUtils;

public class ArchitectureImport {
	final private static JavaClasses classes = new ClassFileImporter().importPackages("fr.ubordeaux.ddd.example..");

	private static String formatMessage(String type, double percentage, String items) {
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append(String.format("Source code does not contain all annotated %s (only %.2f%%)", type, percentage));
		if (items != null) {
			stringBuilder.append(String.format("\nAnnotated %s not found [%s]", type, items));
		}
		return stringBuilder.toString();
	}

	@BeforeClass
	public static void beforeAllTestMethods() {
		ArchitectureImportUtils.importArchitecture();
	}

	@AfterClass
	public static void AfterAllTestMethods() {
		ArchitectureImportUtils.clearArchitecture();
	}

	@Test
	public void compareWithSourceCodeClasses() {
		Map.Entry<Double, String> result = ArchitectureImportUtils.compareWithSourceCodeClasses(classes);
		String message = formatMessage("classes", result.getKey() * 100, result.getValue());
		Assert.assertTrue(message, result.getKey() == 1);
	}

	@Test
	public void compareWithSourceCodeFields() {
		Map.Entry<Double, String> result = ArchitectureImportUtils.compareWithSourceCodeFields(classes);
		String message = formatMessage("fields", result.getKey() * 100, result.getValue());
		Assert.assertTrue(message, result.getKey() == 1);
	}

	@Test
	public void compareWithSourceCodeMethods() {
		Map.Entry<Double, String> result = ArchitectureImportUtils.compareWithSourceCodeMethods(classes);
		String message = formatMessage("methods", result.getKey() * 100, result.getValue());
		Assert.assertTrue(message, result.getKey() == 1);
	}

	@Test
	public void compareWithSourceCode() {
		double result = (ArchitectureImportUtils.compareWithSourceCodeClasses(classes).getKey() + ArchitectureImportUtils.compareWithSourceCodeFields(classes).getKey() + ArchitectureImportUtils.compareWithSourceCodeMethods(classes).getKey()) / 3;
		String message = formatMessage("parts of imported architecture", result * 100, null);
		Assert.assertTrue(message, result == 1);
	}
}