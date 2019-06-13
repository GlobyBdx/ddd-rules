package fr.ubordeaux.ddd.rules;

import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.classes;

import com.tngtech.archunit.lang.ArchRule;

import fr.ubordeaux.ddd.annotations.packages.Domain;
import fr.ubordeaux.ddd.annotations.packages.Infrastructure;
import fr.ubordeaux.ddd.annotations.types.Entity;
import fr.ubordeaux.ddd.annotations.types.Factory;
import fr.ubordeaux.ddd.annotations.types.Repository;
import fr.ubordeaux.ddd.annotations.types.Service;
import fr.ubordeaux.ddd.annotations.types.ValueObject;
import fr.ubordeaux.ddd.rules.utils.ClassUtils;

public class RepositoryRules {
	final public static ArchRule repositories_must_reside_in_a_package_named_infrastructure = ClassUtils.shouldResideInAPackage(Repository.class, "..infrastructure..");

	final public static ArchRule repositories_must_reside_in_a_package_annotated_with_infrastructure = ClassUtils.shouldResideInAPackageAnnotatedWith(Repository.class, Infrastructure.class);

	final public static ArchRule repositories_must_not_also_be_annotated_with_entity = ClassUtils.shouldNotBeAnnotatedWith(Repository.class, Entity.class);

	final public static ArchRule repositories_must_not_also_be_annotated_with_factory = ClassUtils.shouldNotBeAnnotatedWith(Repository.class, Factory.class);

	final public static ArchRule repositories_must_not_also_be_annotated_with_service = ClassUtils.shouldNotBeAnnotatedWith(Repository.class, Service.class);

	final public static ArchRule repositories_must_not_also_be_annotated_with_value_object = ClassUtils.shouldNotBeAnnotatedWith(Repository.class, ValueObject.class);

	final public static ArchRule repositories_must_implement_an_interface_in_a_domain_layer =
		classes()
			.that().areAnnotatedWith(Repository.class)
			.should(ClassUtils.implementAnInterfaceInALayerAnnotatedWith(Domain.class))
			.as("Classes annotated with @" + Repository.class.getSimpleName() + " should implement an interface in a layer annotated with @" + Domain.class.getSimpleName());

	final public static ArchRule repositories_must_access_at_least_one_entity_or_value_object =
		classes()
			.that().areAnnotatedWith(Repository.class)
			.should(ClassUtils.accessAtLeastOneClassAnnotatedWith(Entity.class))
				.orShould(ClassUtils.accessAtLeastOneClassAnnotatedWith(ValueObject.class))
			.as("Classes annotated with @" + Repository.class.getSimpleName() + " should access at least one class annotated with @" + Entity.class.getSimpleName() + " or @" + ValueObject.class.getSimpleName());
}