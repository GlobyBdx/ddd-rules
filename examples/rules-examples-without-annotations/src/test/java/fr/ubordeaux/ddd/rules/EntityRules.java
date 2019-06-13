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

public class EntityRules {
	@ArchTest
	final public static ArchRule entities_must_reside_in_a_package_named_domain = ClassUtils.shouldResideInAPackage("Entity", "..domain..");

	@ArchTest
	final public static ArchRule entities_must_reside_in_a_package_annotated_with_domain = ClassUtils.shouldResideInAPackageAnnotatedWith("Entity", "Domain");

	@ArchTest
	final public static ArchRule entities_must_not_also_be_annotated_with_factory = ClassUtils.shouldNotBeAnnotatedWith("Entity", "Factory");

	@ArchTest
	final public static ArchRule entities_must_not_also_be_annotated_with_repository = ClassUtils.shouldNotBeAnnotatedWith("Entity", "Repository");

	@ArchTest
	final public static ArchRule entities_must_not_also_be_annotated_with_service = ClassUtils.shouldNotBeAnnotatedWith("Entity", "Service");

	@ArchTest
	final public static ArchRule entities_must_not_also_be_annotated_with_value_object = ClassUtils.shouldNotBeAnnotatedWith("Entity", "ValueObject");

	@ArchTest
	final public static ArchRule entities_must_have_at_least_one_entity_id =
		classes()
			.that(ClassUtils.areAnnotatedWith("Entity"))
			.should(ClassUtils.haveAtLeastOneFieldAnnotatedWith("EntityID"))
			.as("Classes annotated with @Entity should have at least one field annotated with @EntityID");

	@ArchTest
	final public static ArchRule entities_must_override_object_class_equals_method_accessing_all_entity_ids =
		classes()
		.that(ClassUtils.areAnnotatedWith("Entity"))
			.should(ClassUtils.overrideObjectClassEqualsMethodAccessingAllEntityIDs)
			.as("Classes annotated with @Entity should override Object.class equals method accessing all fields annotated with @EntityID");

	@ArchTest
	final public static ArchRule entities_must_override_object_class_hash_code_method_accessing_all_entity_ids =
		classes()
		.that(ClassUtils.areAnnotatedWith("Entity"))
			.should(ClassUtils.overrideObjectClassHashCodeMethodAccessingAllEntityIDs)
			.as("Classes annotated with @Entity should override Object.class hashCode method accessing all fields annotated with @EntityID");

	@ArchTest
	final public static ArchRule entities_must_override_object_class_to_string_method_accessing_all_entity_ids =
		classes()
		.that(ClassUtils.areAnnotatedWith("Entity"))
			.should(ClassUtils.overrideObjectClassToStringMethodAccessingAllEntityIDs)
			.as("Classes annotated with @Entity should override Object.class toString method accessing all fields annotated with @EntityID");
}