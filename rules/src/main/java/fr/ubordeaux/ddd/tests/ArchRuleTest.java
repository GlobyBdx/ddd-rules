package fr.ubordeaux.ddd.tests;

@FunctionalInterface
public interface ArchRuleTest {
	final public static String SRC_CLASSES_FOLDER = "/classes";
	final public static String TEST_CLASSES_FOLDER = "/test-classes";

	public void execute(String path);
}