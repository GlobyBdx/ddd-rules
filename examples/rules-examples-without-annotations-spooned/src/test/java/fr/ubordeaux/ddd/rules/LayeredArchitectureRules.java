package fr.ubordeaux.ddd.rules;

import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.classes;
import static com.tngtech.archunit.library.Architectures.layeredArchitecture;

import com.tngtech.archunit.junit.AnalyzeClasses;
import com.tngtech.archunit.junit.ArchTest;
import com.tngtech.archunit.junit.ArchUnitRunner;
import com.tngtech.archunit.lang.ArchRule;

import fr.ubordeaux.ddd.annotations.packages.Anticorruption;
import fr.ubordeaux.ddd.annotations.packages.Application;
import fr.ubordeaux.ddd.annotations.packages.Domain;
import fr.ubordeaux.ddd.annotations.packages.Infrastructure;
import fr.ubordeaux.ddd.annotations.packages.Presentation;
import fr.ubordeaux.ddd.rules.utils.PackageUtils;

import org.junit.runner.RunWith;

@RunWith(ArchUnitRunner.class)
@AnalyzeClasses(packages = "fr.ubordeaux.ddd.example")

public class LayeredArchitectureRules {
	@ArchTest
	final public static ArchRule named_anticorruption_layer_classes_must_not_reside_in_another_layer =
		classes()
			.that().resideInAPackage("..anticorruption..")
			.should().resideOutsideOfPackage("..presentation..")
				.andShould().resideOutsideOfPackage("..application..")
				.andShould().resideOutsideOfPackage("..domain..")
				.andShould().resideOutsideOfPackage("..infrastructure..")
			.as("Anticorruption layer classes should not reside in another layer");

	@ArchTest
	final public static ArchRule annotated_anticorruption_layer_classes_must_not_reside_in_another_layer =
		classes()
			.that().resideInAnyPackage(PackageUtils.getAllPackagesAnnotatedWith(Anticorruption.class))
			.should().resideOutsideOfPackages(PackageUtils.getAllPackagesAnnotatedWith(Presentation.class))
				.andShould().resideOutsideOfPackages(PackageUtils.getAllPackagesAnnotatedWith(Application.class))
				.andShould().resideOutsideOfPackages(PackageUtils.getAllPackagesAnnotatedWith(Domain.class))
				.andShould().resideOutsideOfPackages(PackageUtils.getAllPackagesAnnotatedWith(Infrastructure.class))
			.as("Anticorruption layer classes should not reside in another layer");

	@ArchTest
	final public static ArchRule named_anticorruption_layer_dependencies_must_be_respected =
		layeredArchitecture()
			.layer("Anticorruption").definedBy("..anticorruption..")
			.whereLayer("Anticorruption").mayNotBeAccessedByAnyLayer()
			.as("Anticorruption layer dependencies should be respected");

	@ArchTest
	final public static ArchRule annotated_anticorruption_layer_dependencies_must_be_respected =
		layeredArchitecture()
			.layer("Anticorruption").definedBy(PackageUtils.getAllPackagesAnnotatedWith(Anticorruption.class))
			.whereLayer("Anticorruption").mayNotBeAccessedByAnyLayer()
			.as("Anticorruption layer dependencies should be respected");

	@ArchTest
	final public static ArchRule named_presentation_layer_classes_must_not_reside_in_another_layer =
		classes()
			.that().resideInAPackage("..presentation..")
			.should().resideOutsideOfPackage("..anticorruption..")
				.andShould().resideOutsideOfPackage("..application..")
				.andShould().resideOutsideOfPackage("..domain..")
				.andShould().resideOutsideOfPackage("..infrastructure..")
			.as("Presentation layer classes should not reside in another layer");

	@ArchTest
	final public static ArchRule annotated_presentation_layer_classes_must_not_reside_in_another_layer =
		classes()
			.that().resideInAnyPackage(PackageUtils.getAllPackagesAnnotatedWith(Presentation.class))
			.should().resideOutsideOfPackages(PackageUtils.getAllPackagesAnnotatedWith(Anticorruption.class))
				.andShould().resideOutsideOfPackages(PackageUtils.getAllPackagesAnnotatedWith(Application.class))
				.andShould().resideOutsideOfPackages(PackageUtils.getAllPackagesAnnotatedWith(Domain.class))
				.andShould().resideOutsideOfPackages(PackageUtils.getAllPackagesAnnotatedWith(Infrastructure.class))
			.as("Presentation layer classes should not reside in another layer");

	@ArchTest
	final public static ArchRule named_presentation_layer_dependencies_must_be_respected =
		layeredArchitecture()
			.layer("Presentation").definedBy("..presentation..")
			.whereLayer("Presentation").mayNotBeAccessedByAnyLayer()
			.as("Presentation layer dependencies should be respected");

	@ArchTest
	final public static ArchRule annotated_presentation_layer_dependencies_must_be_respected =
		layeredArchitecture()
			.layer("Presentation").definedBy(PackageUtils.getAllPackagesAnnotatedWith(Presentation.class))
			.whereLayer("Presentation").mayNotBeAccessedByAnyLayer()
			.as("Presentation layer dependencies should be respected");

	@ArchTest
	final public static ArchRule named_application_layer_classes_must_not_reside_in_another_layer =
		classes()
			.that().resideInAPackage("..application..")
			.should().resideOutsideOfPackage("..anticorruption..")
				.andShould().resideOutsideOfPackage("..presentation..")
				.andShould().resideOutsideOfPackage("..domain..")
				.andShould().resideOutsideOfPackage("..infrastructure..")
			.as("Application layer classes should not reside in another layer");

	@ArchTest
	final public static ArchRule annotated_application_layer_classes_must_not_reside_in_another_layer =
		classes()
			.that().resideInAnyPackage(PackageUtils.getAllPackagesAnnotatedWith(Application.class))
			.should().resideOutsideOfPackages(PackageUtils.getAllPackagesAnnotatedWith(Anticorruption.class))
				.andShould().resideOutsideOfPackages(PackageUtils.getAllPackagesAnnotatedWith(Presentation.class))
				.andShould().resideOutsideOfPackages(PackageUtils.getAllPackagesAnnotatedWith(Domain.class))
				.andShould().resideOutsideOfPackages(PackageUtils.getAllPackagesAnnotatedWith(Infrastructure.class))
			.as("Application layer classes should not reside in another layer");

	@ArchTest
	final public static ArchRule named_application_layer_dependencies_must_be_respected =
		layeredArchitecture()
			.layer("Application").definedBy("..application..")
			.layer("Anticorruption").definedBy("..anticorruption..")
			.layer("Presentation").definedBy("..presentation..")
			.whereLayer("Application").mayOnlyBeAccessedByLayers("Anticorruption", "Presentation")
			.as("Application layer dependencies should be respected");

	@ArchTest
	final public static ArchRule annotated_application_layer_dependencies_must_be_respected =
		layeredArchitecture()
			.layer("Application").definedBy(PackageUtils.getAllPackagesAnnotatedWith(Application.class))
			.layer("Anticorruption").definedBy(PackageUtils.getAllPackagesAnnotatedWith(Anticorruption.class))
			.layer("Presentation").definedBy(PackageUtils.getAllPackagesAnnotatedWith(Presentation.class))
			.whereLayer("Application").mayOnlyBeAccessedByLayers("Anticorruption", "Presentation")
			.as("Application layer dependencies should be respected");

	@ArchTest
	final public static ArchRule named_domain_layer_classes_must_not_reside_in_another_layer =
		classes()
			.that().resideInAPackage("..domain..")
			.should().resideOutsideOfPackage("..anticorruption..")
				.andShould().resideOutsideOfPackage("..presentation..")
				.andShould().resideOutsideOfPackage("..application..")
				.andShould().resideOutsideOfPackage("..infrastructure..")
			.as("Domain layer classes should not reside in another layer");

	@ArchTest
	final public static ArchRule annotated_domain_layer_classes_must_not_reside_in_another_layer =
		classes()
			.that().resideInAnyPackage(PackageUtils.getAllPackagesAnnotatedWith(Domain.class))
			.should().resideOutsideOfPackages(PackageUtils.getAllPackagesAnnotatedWith(Anticorruption.class))
				.andShould().resideOutsideOfPackages(PackageUtils.getAllPackagesAnnotatedWith(Presentation.class))
				.andShould().resideOutsideOfPackages(PackageUtils.getAllPackagesAnnotatedWith(Application.class))
				.andShould().resideOutsideOfPackages(PackageUtils.getAllPackagesAnnotatedWith(Infrastructure.class))
			.as("Domain layer classes should not reside in another layer");

	@ArchTest
	final public static ArchRule named_infrastructure_layer_classes_must_not_reside_in_another_layer =
		classes()
			.that().resideInAPackage("..infrastructure..")
			.should().resideOutsideOfPackage("..anticorruption..")
				.andShould().resideOutsideOfPackage("..presentation..")
				.andShould().resideOutsideOfPackage("..application..")
				.andShould().resideOutsideOfPackage("..domain..")
			.as("Infrastructure layer classes should not reside in another layer");

	@ArchTest
	final public static ArchRule annotated_infrastructure_layer_classes_must_not_reside_in_another_layer =
		classes()
			.that().resideInAnyPackage(PackageUtils.getAllPackagesAnnotatedWith(Infrastructure.class))
			.should().resideOutsideOfPackages(PackageUtils.getAllPackagesAnnotatedWith(Anticorruption.class))
				.andShould().resideOutsideOfPackages(PackageUtils.getAllPackagesAnnotatedWith(Presentation.class))
				.andShould().resideOutsideOfPackages(PackageUtils.getAllPackagesAnnotatedWith(Application.class))
				.andShould().resideOutsideOfPackages(PackageUtils.getAllPackagesAnnotatedWith(Domain.class))
			.as("Infrastructure layer classes should not reside in another layer");

	@ArchTest
	final public static ArchRule named_infrastructure_layer_dependencies_must_be_respected =
		layeredArchitecture()
			.layer("Infrastructure").definedBy("..infrastructure..")
			.layer("Anticorruption").definedBy("..anticorruption..")
			.layer("Presentation").definedBy("..presentation..")
			.layer("Application").definedBy("..application..")
			.whereLayer("Infrastructure").mayOnlyBeAccessedByLayers("Anticorruption", "Presentation", "Application")
			.as("Infrastructure layer dependencies should be respected");

	@ArchTest
	final public static ArchRule annotated_infrastructure_layer_dependencies_must_be_respected =
		layeredArchitecture()
			.layer("Infrastructure").definedBy(PackageUtils.getAllPackagesAnnotatedWith(Infrastructure.class))
			.layer("Anticorruption").definedBy(PackageUtils.getAllPackagesAnnotatedWith(Anticorruption.class))
			.layer("Presentation").definedBy(PackageUtils.getAllPackagesAnnotatedWith(Presentation.class))
			.layer("Application").definedBy(PackageUtils.getAllPackagesAnnotatedWith(Application.class))
			.whereLayer("Infrastructure").mayOnlyBeAccessedByLayers("Anticorruption", "Presentation", "Application")
			.as("Infrastructure layer dependencies should be respected");
}