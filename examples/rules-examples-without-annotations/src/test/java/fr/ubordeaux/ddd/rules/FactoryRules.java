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

public class FactoryRules {
	@ArchTest
	final public static ArchRule factories_must_reside_in_a_package_named_domain = ClassUtils.shouldResideInAPackage("Factory", "..domain..");

	@ArchTest
	final public static ArchRule factories_must_reside_in_a_package_annotated_with_domain = ClassUtils.shouldResideInAPackageAnnotatedWith("Factory", "Domain");

	@ArchTest
	final public static ArchRule factories_must_not_also_be_annotated_with_entity = ClassUtils.shouldNotBeAnnotatedWith("Factory", "Entity");

	@ArchTest
	final public static ArchRule factories_must_not_also_be_annotated_with_repository = ClassUtils.shouldNotBeAnnotatedWith("Factory", "Repository");

	@ArchTest
	final public static ArchRule factories_must_not_also_be_annotated_with_service = ClassUtils.shouldNotBeAnnotatedWith("Factory", "Service");

	@ArchTest
	final public static ArchRule factories_must_not_also_be_annotated_with_value_object = ClassUtils.shouldNotBeAnnotatedWith("Factory", "ValueObject");

	@ArchTest
	final public static ArchRule factories_must_access_at_least_one_constructor_from_an_entity_or_a_value_object =
		classes()
			.that(ClassUtils.areAnnotatedWith("Factory"))
			.should(ClassUtils.accessAtLeastOneConstructorFromAClassAnnotatedWith("Entity"))
				.orShould(ClassUtils.accessAtLeastOneConstructorFromAClassAnnotatedWith("ValueObject"))
			.as("Classes annotated with @Factory should access at least one constructor from a class annotated with @Entity or @ValueObject");

	@ArchTest
	final public static ArchRule factories_must_have_public_and_protected_methods_with_the_same_return_type =
		classes()
		.that(ClassUtils.areAnnotatedWith("Factory"))
			.should(ClassUtils.havePublicAndProtectedMethodsWithTheSameReturnType)
			.as("Classes annotated with @Factory should have public and protected methods with the same return type");
}