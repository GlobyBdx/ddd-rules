package fr.ubordeaux.ddd.rules;

import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.classes;

import com.tngtech.archunit.lang.ArchRule;

import fr.ubordeaux.ddd.annotations.fields.EntityID;
import fr.ubordeaux.ddd.annotations.packages.Domain;
import fr.ubordeaux.ddd.annotations.types.Entity;
import fr.ubordeaux.ddd.annotations.types.Factory;
import fr.ubordeaux.ddd.annotations.types.Repository;
import fr.ubordeaux.ddd.annotations.types.Service;
import fr.ubordeaux.ddd.annotations.types.ValueObject;
import fr.ubordeaux.ddd.rules.utils.ClassUtils;

public class EntityRules {
	final public static ArchRule entities_must_reside_in_a_package_named_domain = ClassUtils.shouldResideInAPackage(Entity.class, "..domain..");

	final public static ArchRule entities_must_reside_in_a_package_annotated_with_domain = ClassUtils.shouldResideInAPackageAnnotatedWith(Entity.class, Domain.class);

	final public static ArchRule entities_must_not_also_be_annotated_with_factory = ClassUtils.shouldNotBeAnnotatedWith(Entity.class, Factory.class);

	final public static ArchRule entities_must_not_also_be_annotated_with_repository = ClassUtils.shouldNotBeAnnotatedWith(Entity.class, Repository.class);

	final public static ArchRule entities_must_not_also_be_annotated_with_service = ClassUtils.shouldNotBeAnnotatedWith(Entity.class, Service.class);

	final public static ArchRule entities_must_not_also_be_annotated_with_value_object = ClassUtils.shouldNotBeAnnotatedWith(Entity.class, ValueObject.class);

	final public static ArchRule entities_must_have_at_least_one_entity_id =
		classes()
			.that().areAnnotatedWith(Entity.class)
			.should(ClassUtils.haveAtLeastOneFieldAnnotatedWith(EntityID.class))
			.as("Classes annotated with @" + Entity.class.getSimpleName() + " should have at least one field annotated with @" + EntityID.class.getSimpleName());

	final public static ArchRule entities_must_override_object_class_equals_method_accessing_all_entity_ids =
		classes()
			.that().areAnnotatedWith(Entity.class)
			.should(ClassUtils.overrideObjectClassEqualsMethodAccessingAllEntityIDs)
			.as("Classes annotated with @" + Entity.class.getSimpleName() + " should override Object.class equals method accessing all fields annotated with @" + EntityID.class.getSimpleName());

	final public static ArchRule entities_must_override_object_class_hash_code_method_accessing_all_entity_ids =
		classes()
			.that().areAnnotatedWith(Entity.class)
			.should(ClassUtils.overrideObjectClassHashCodeMethodAccessingAllEntityIDs)
			.as("Classes annotated with @" + Entity.class.getSimpleName() + " should override Object.class hashCode method accessing all fields annotated with @" + EntityID.class.getSimpleName());

	final public static ArchRule entities_must_override_object_class_to_string_method_accessing_all_entity_ids =
		classes()
			.that().areAnnotatedWith(Entity.class)
			.should(ClassUtils.overrideObjectClassToStringMethodAccessingAllEntityIDs)
			.as("Classes annotated with @" + Entity.class.getSimpleName() + " should override Object.class toString method accessing all fields annotated with @" + EntityID.class.getSimpleName());
}