package fr.ubordeaux.ddd.rules;

import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.classes;

import com.tngtech.archunit.junit.AnalyzeClasses;
import com.tngtech.archunit.junit.ArchTest;
import com.tngtech.archunit.junit.ArchUnitRunner;
import com.tngtech.archunit.lang.ArchRule;

import fr.ubordeaux.ddd.annotations.packages.Domain;
import fr.ubordeaux.ddd.annotations.types.Entity;
import fr.ubordeaux.ddd.annotations.types.Factory;
import fr.ubordeaux.ddd.annotations.types.Repository;
import fr.ubordeaux.ddd.annotations.types.Service;
import fr.ubordeaux.ddd.annotations.types.ValueObject;
import fr.ubordeaux.ddd.rules.utils.ClassUtils;

import org.junit.runner.RunWith;

@RunWith(ArchUnitRunner.class)
@AnalyzeClasses(packages = "fr.ubordeaux.ddd.example")

public class FactoryRules {
	@ArchTest
	final public static ArchRule factories_must_reside_in_a_package_named_domain = ClassUtils.shouldResideInAPackage(Factory.class, "..domain..");

	@ArchTest
	final public static ArchRule factories_must_reside_in_a_package_annotated_with_domain = ClassUtils.shouldResideInAPackageAnnotatedWith(Factory.class, Domain.class);

	@ArchTest
	final public static ArchRule factories_must_not_also_be_annotated_with_entity = ClassUtils.shouldNotBeAnnotatedWith(Factory.class, Entity.class);

	@ArchTest
	final public static ArchRule factories_must_not_also_be_annotated_with_repository = ClassUtils.shouldNotBeAnnotatedWith(Factory.class, Repository.class);

	@ArchTest
	final public static ArchRule factories_must_not_also_be_annotated_with_service = ClassUtils.shouldNotBeAnnotatedWith(Factory.class, Service.class);

	@ArchTest
	final public static ArchRule factories_must_not_also_be_annotated_with_value_object = ClassUtils.shouldNotBeAnnotatedWith(Factory.class, ValueObject.class);

	@ArchTest
	final public static ArchRule factories_must_access_at_least_one_constructor_from_an_entity_or_a_value_object =
		classes()
			.that().areAnnotatedWith(Factory.class)
			.should(ClassUtils.accessAtLeastOneConstructorFromAClassAnnotatedWith(Entity.class))
				.orShould(ClassUtils.accessAtLeastOneConstructorFromAClassAnnotatedWith(ValueObject.class))
			.as("Classes annotated with @" + Factory.class.getSimpleName() + " should access at least one constructor from a class annotated with @" + Entity.class.getSimpleName() + " or @" + ValueObject.class.getSimpleName());

	@ArchTest
	final public static ArchRule factories_must_have_public_and_protected_methods_with_the_same_return_type =
		classes()
			.that().areAnnotatedWith(Factory.class)
			.should(ClassUtils.havePublicAndProtectedMethodsWithTheSameReturnType)
			.as("Classes annotated with @" + Factory.class.getSimpleName() + " should have public and protected methods with the same return type");
}