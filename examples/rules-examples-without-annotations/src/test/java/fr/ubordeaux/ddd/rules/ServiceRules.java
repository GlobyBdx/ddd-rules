package fr.ubordeaux.ddd.rules;

import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.classes;

import com.tngtech.archunit.junit.AnalyzeClasses;
import com.tngtech.archunit.junit.ArchTest;
import com.tngtech.archunit.junit.ArchUnitRunner;
import com.tngtech.archunit.lang.ArchRule;

import fr.ubordeaux.ddd.rules.utils.ClassUtils;

import org.junit.runner.RunWith;

@RunWith(ArchUnitRunner.class)
@AnalyzeClasses(packages = "fr.ubordeaux.ddd.example")

public class ServiceRules {
	@ArchTest
	final public static ArchRule services_must_reside_in_a_package_named_application_or_domain_or_infrastructure =
		classes()
			.that(ClassUtils.areAnnotatedWith("Service"))
			.should().resideInAPackage("..application..")
				.orShould().resideInAPackage("..domain..")
				.orShould().resideInAPackage("..infrastructure..")
			.as("Classes annotated with @Service should reside in a package named '..application..' or '..domain..' or '..infrastructure..'");

	@ArchTest
	final public static ArchRule services_must_reside_in_a_package_annotated_with_application_or_domain_or_infrastructure =
		classes()
			.that(ClassUtils.areAnnotatedWith("Service"))
			.should(ClassUtils.resideInAPackageAnnotatedWith("Application"))
				.orShould(ClassUtils.resideInAPackageAnnotatedWith("Domain"))
				.orShould(ClassUtils.resideInAPackageAnnotatedWith("Infrastructure"))
			.as("Classes annotated with @Service should reside in a package annotated with @Application or @Domain or @Infrastructure");

	@ArchTest
	final public static ArchRule services_must_not_also_be_annotated_with_entity = ClassUtils.shouldNotBeAnnotatedWith("Service", "Entity");

	@ArchTest
	final public static ArchRule services_must_not_also_be_annotated_with_factory = ClassUtils.shouldNotBeAnnotatedWith("Service", "Factory");

	@ArchTest
	final public static ArchRule services_must_not_also_be_annotated_with_repository = ClassUtils.shouldNotBeAnnotatedWith("Service", "Repository");

	@ArchTest
	final public static ArchRule services_must_not_also_be_annotated_with_value_object = ClassUtils.shouldNotBeAnnotatedWith("Service", "ValueObject");

	@ArchTest
	final public static ArchRule services_must_implement_an_interface_in_the_same_layer =
		classes()
			.that(ClassUtils.areAnnotatedWith("Service"))
			.should(ClassUtils.implementAnInterfaceInTheSameLayer)
			.as("Classes annotated with @Service should implement an interface in the same layer");

	@ArchTest
	final public static ArchRule services_fields_must_be_final =
		classes()
			.that(ClassUtils.areAnnotatedWith("Service"))
			.should().haveOnlyFinalFields()
			.as("Classes annotated with @Service fields should be final");
}