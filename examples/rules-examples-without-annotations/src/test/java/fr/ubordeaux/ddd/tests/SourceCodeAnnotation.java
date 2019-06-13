package fr.ubordeaux.ddd.tests;

import fr.ubordeaux.ddd.rules.utils.PackageUtils;
import fr.ubordeaux.ddd.tests.utils.AggregateAnnotationProcessor;
import fr.ubordeaux.ddd.tests.utils.ArchitectureImportUtils;
import fr.ubordeaux.ddd.tests.utils.EntityAnnotationProcessor;
import fr.ubordeaux.ddd.tests.utils.EntityIdAnnotationProcessor;
import fr.ubordeaux.ddd.tests.utils.FactoryAnnotationProcessor;
import fr.ubordeaux.ddd.tests.utils.GetterAnnotationProcessor;
import fr.ubordeaux.ddd.tests.utils.ImmutableAnnotationProcessor;
import fr.ubordeaux.ddd.tests.utils.LayeredArchitectureAnnotationProcessor;
import fr.ubordeaux.ddd.tests.utils.RepositoryAnnotationProcessor;
import fr.ubordeaux.ddd.tests.utils.ServiceAnnotationProcessor;
import fr.ubordeaux.ddd.tests.utils.SetterAnnotationProcessor;
import fr.ubordeaux.ddd.tests.utils.SourceCodeAnnotationUtils;
import fr.ubordeaux.ddd.tests.utils.ValueObjectAnnotationProcessor;
import spoon.Launcher;

import java.io.File;

import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class SourceCodeAnnotation {
	@BeforeClass
	public static void beforeAllTestMethods() {
		ArchitectureImportUtils.importPackages();
		SourceCodeAnnotationUtils.createPackageInfoFiles();
	}

	@AfterClass
	public static void afterAllTestMethods() {
		SourceCodeAnnotationUtils.deletePackageInfoFiles();
		ArchitectureImportUtils.clearPackages();
	}

	@Test
	public void annotateSourceCode() throws Exception {
		final Launcher launcher = new Launcher();
		launcher.addProcessor(new AggregateAnnotationProcessor());
		launcher.addProcessor(new EntityAnnotationProcessor());
		launcher.addProcessor(new EntityIdAnnotationProcessor());
		launcher.addProcessor(new FactoryAnnotationProcessor());
		launcher.addProcessor(new GetterAnnotationProcessor());
		launcher.addProcessor(new ImmutableAnnotationProcessor());
		launcher.addProcessor(new LayeredArchitectureAnnotationProcessor());
		launcher.addProcessor(new RepositoryAnnotationProcessor());
		launcher.addProcessor(new ServiceAnnotationProcessor());
		launcher.addProcessor(new SetterAnnotationProcessor());
		launcher.addProcessor(new ValueObjectAnnotationProcessor());
		launcher.addInputResource(SourceCodeAnnotationUtils.ROOT + "fr/ubordeaux/ddd/example/");
		launcher.setSourceOutputDirectory("../ddd-rules-examples-without-annotations-spooned/" + SourceCodeAnnotationUtils.ROOT);
		launcher.getEnvironment().setAutoImports(true);
		launcher.run();
	}

	@Test
	public final void checkSpoonedVersion() {
		Assert.assertTrue(new File("../ddd-rules-examples-without-annotations-spooned/" + SourceCodeAnnotationUtils.ROOT + "fr/ubordeaux/ddd/example/anticorruption/application/domain/infrastructure/presentation/" + PackageUtils.PACKAGE_INFO + SourceCodeAnnotationUtils.EXTENSION).isFile());
		Assert.assertTrue(new File("../ddd-rules-examples-without-annotations-spooned/" + SourceCodeAnnotationUtils.ROOT + "fr/ubordeaux/ddd/example/anticorruption/application/domain/infrastructure/presentation/evil/EvilMultipleLayersToken" + SourceCodeAnnotationUtils.EXTENSION).isFile());
	}
}