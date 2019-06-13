package fr.ubordeaux.ddd.rules;

import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.classes;

import com.tngtech.archunit.junit.AnalyzeClasses;
import com.tngtech.archunit.junit.ArchTest;
import com.tngtech.archunit.junit.ArchUnitRunner;
import com.tngtech.archunit.lang.ArchRule;

import fr.ubordeaux.ddd.annotations.packages.Application;
import fr.ubordeaux.ddd.annotations.packages.Domain;
import fr.ubordeaux.ddd.annotations.packages.Infrastructure;
import fr.ubordeaux.ddd.annotations.types.Entity;
import fr.ubordeaux.ddd.annotations.types.Factory;
import fr.ubordeaux.ddd.annotations.types.Repository;
import fr.ubordeaux.ddd.annotations.types.Service;
import fr.ubordeaux.ddd.annotations.types.ValueObject;
import fr.ubordeaux.ddd.rules.utils.ClassUtils;

import org.junit.runner.RunWith;

@RunWith(ArchUnitRunner.class)
@AnalyzeClasses(packages = "fr.ubordeaux.ddd.example")

public class ServiceRules {
	@ArchTest
	final public static ArchRule services_must_reside_in_a_package_named_application_or_domain_or_infrastructure =
		classes()
			.that().areAnnotatedWith(Service.class)
			.should().resideInAPackage("..application..")
				.orShould().resideInAPackage("..domain..")
				.orShould().resideInAPackage("..infrastructure..")
			.as("Classes annotated with @" + Service.class.getSimpleName() + " should reside in a package named '..application..' or '..domain..' or '..infrastructure..'");

	@ArchTest
	final public static ArchRule services_must_reside_in_a_package_annotated_with_application_or_domain_or_infrastructure =
		classes()
			.that().areAnnotatedWith(Service.class)
			.should(ClassUtils.resideInAPackageAnnotatedWith(Application.class))
				.orShould(ClassUtils.resideInAPackageAnnotatedWith(Domain.class))
				.orShould(ClassUtils.resideInAPackageAnnotatedWith(Infrastructure.class))
			.as("Classes annotated with @" + Service.class.getSimpleName() + " should reside in a package annotated with @" + Application.class.getSimpleName() + " or @" + Domain.class.getSimpleName() + " or @" + Infrastructure.class.getSimpleName());

	@ArchTest
	final public static ArchRule services_must_not_also_be_annotated_with_entity = ClassUtils.shouldNotBeAnnotatedWith(Service.class, Entity.class);

	@ArchTest
	final public static ArchRule services_must_not_also_be_annotated_with_factory = ClassUtils.shouldNotBeAnnotatedWith(Service.class, Factory.class);

	@ArchTest
	final public static ArchRule services_must_not_also_be_annotated_with_repository = ClassUtils.shouldNotBeAnnotatedWith(Service.class, Repository.class);

	@ArchTest
	final public static ArchRule services_must_not_also_be_annotated_with_value_object = ClassUtils.shouldNotBeAnnotatedWith(Service.class, ValueObject.class);

	@ArchTest
	final public static ArchRule services_must_implement_an_interface_in_the_same_layer =
		classes()
			.that().areAnnotatedWith(Service.class)
			.should(ClassUtils.implementAnInterfaceInTheSameLayer)
			.as("Classes annotated with @" + Service.class.getSimpleName() + " should implement an interface in the same layer");

	@ArchTest
	final public static ArchRule services_fields_must_be_final =
		classes()
			.that().areAnnotatedWith(Service.class)
			.should().haveOnlyFinalFields()
			.as("Classes annotated with @" + Service.class.getSimpleName() + " fields should be final");
}