package fr.ubordeaux.ddd.rules;

import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.classes;

import com.tngtech.archunit.junit.AnalyzeClasses;
import com.tngtech.archunit.junit.ArchTest;
import com.tngtech.archunit.junit.ArchUnitRunner;
import com.tngtech.archunit.lang.ArchRule;

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

public class RepositoryRules {
	@ArchTest
	final public static ArchRule repositories_must_reside_in_a_package_named_infrastructure = ClassUtils.shouldResideInAPackage(Repository.class, "..infrastructure..");

	@ArchTest
	final public static ArchRule repositories_must_reside_in_a_package_annotated_with_infrastructure = ClassUtils.shouldResideInAPackageAnnotatedWith(Repository.class, Infrastructure.class);

	@ArchTest
	final public static ArchRule repositories_must_not_also_be_annotated_with_entity = ClassUtils.shouldNotBeAnnotatedWith(Repository.class, Entity.class);

	@ArchTest
	final public static ArchRule repositories_must_not_also_be_annotated_with_factory = ClassUtils.shouldNotBeAnnotatedWith(Repository.class, Factory.class);

	@ArchTest
	final public static ArchRule repositories_must_not_also_be_annotated_with_service = ClassUtils.shouldNotBeAnnotatedWith(Repository.class, Service.class);

	@ArchTest
	final public static ArchRule repositories_must_not_also_be_annotated_with_value_object = ClassUtils.shouldNotBeAnnotatedWith(Repository.class, ValueObject.class);

	@ArchTest
	final public static ArchRule repositories_must_implement_an_interface_in_a_domain_layer =
		classes()
			.that().areAnnotatedWith(Repository.class)
			.should(ClassUtils.implementAnInterfaceInALayerAnnotatedWith(Domain.class))
			.as("Classes annotated with @" + Repository.class.getSimpleName() + " should implement an interface in a layer annotated with @" + Domain.class.getSimpleName());

	@ArchTest
	final public static ArchRule repositories_must_access_at_least_one_entity_or_value_object =
		classes()
			.that().areAnnotatedWith(Repository.class)
			.should(ClassUtils.accessAtLeastOneClassAnnotatedWith(Entity.class))
				.orShould(ClassUtils.accessAtLeastOneClassAnnotatedWith(ValueObject.class))
			.as("Classes annotated with @" + Repository.class.getSimpleName() + " should access at least one class annotated with @" + Entity.class.getSimpleName() + " or @" + ValueObject.class.getSimpleName());
}