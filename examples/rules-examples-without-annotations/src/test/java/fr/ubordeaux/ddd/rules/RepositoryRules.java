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

public class RepositoryRules {
	@ArchTest
	final public static ArchRule repositories_must_reside_in_a_package_named_infrastructure = ClassUtils.shouldResideInAPackage("Repository", "..infrastructure..");

	@ArchTest
	final public static ArchRule repositories_must_reside_in_a_package_annotated_with_infrastructure = ClassUtils.shouldResideInAPackageAnnotatedWith("Repository", "Infrastructure");

	@ArchTest
	final public static ArchRule repositories_must_not_also_be_annotated_with_entity = ClassUtils.shouldNotBeAnnotatedWith("Repository", "Entity");

	@ArchTest
	final public static ArchRule repositories_must_not_also_be_annotated_with_factory = ClassUtils.shouldNotBeAnnotatedWith("Repository", "Factory");

	@ArchTest
	final public static ArchRule repositories_must_not_also_be_annotated_with_service = ClassUtils.shouldNotBeAnnotatedWith("Repository", "Service");

	@ArchTest
	final public static ArchRule repositories_must_not_also_be_annotated_with_value_object = ClassUtils.shouldNotBeAnnotatedWith("Repository", "ValueObject");

	@ArchTest
	final public static ArchRule repositories_must_implement_an_interface_in_a_domain_layer =
		classes()
			.that(ClassUtils.areAnnotatedWith("Repository"))
			.should(ClassUtils.implementAnInterfaceInALayerAnnotatedWith("Domain"))
			.as("Classes annotated with @Repository should implement an interface in a layer annotated with @Domain");

	@ArchTest
	final public static ArchRule repositories_must_access_at_least_one_entity_or_value_object =
		classes()
			.that(ClassUtils.areAnnotatedWith("Repository"))
			.should(ClassUtils.accessAtLeastOneClassAnnotatedWith("Entity"))
				.orShould(ClassUtils.accessAtLeastOneClassAnnotatedWith("ValueObject"))
			.as("Classes annotated with @Repository should access at least one class annotated with @Entity or @ValueObject");
}