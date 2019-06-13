package fr.ubordeaux.ddd.rules;

import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.classes;
import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.fields;
import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.methods;

import com.tngtech.archunit.junit.AnalyzeClasses;
import com.tngtech.archunit.junit.ArchTest;
import com.tngtech.archunit.junit.ArchUnitRunner;
import com.tngtech.archunit.lang.ArchRule;

import fr.ubordeaux.ddd.rules.utils.ClassUtils;
import fr.ubordeaux.ddd.rules.utils.MethodUtils;

import org.junit.runner.RunWith;

@RunWith(ArchUnitRunner.class)
@AnalyzeClasses(packages = "fr.ubordeaux.ddd.example")

public class ValueObjectRules {
	@ArchTest
	final public static ArchRule value_objects_must_reside_in_a_package_named_domain = ClassUtils.shouldResideInAPackage("ValueObject", "..domain..");

	@ArchTest
	final public static ArchRule value_objects_must_reside_in_a_package_annotated_with_domain = ClassUtils.shouldResideInAPackageAnnotatedWith("ValueObject", "Domain");

	@ArchTest
	final public static ArchRule value_objects_must_not_also_be_annotated_with_entity = ClassUtils.shouldNotBeAnnotatedWith("ValueObject", "Entity");

	@ArchTest
	final public static ArchRule value_objects_must_not_also_be_annotated_with_factory = ClassUtils.shouldNotBeAnnotatedWith("ValueObject", "Factory");

	@ArchTest
	final public static ArchRule value_objects_must_not_also_be_annotated_with_repository = ClassUtils.shouldNotBeAnnotatedWith("ValueObject", "Repository");

	@ArchTest
	final public static ArchRule value_objects_must_not_also_be_annotated_with_service = ClassUtils.shouldNotBeAnnotatedWith("ValueObject", "Service");

	@ArchTest
	final public static ArchRule value_objects_must_have_at_least_one_field =
		classes()
			.that(ClassUtils.areAnnotatedWith("ValueObject"))
			.should(ClassUtils.haveAtLeastOneField)
			.as("Classes annotated with @ValueObject should have at least one field");

	@ArchTest
	final public static ArchRule value_objects_must_override_object_class_equals_method_accessing_all_fields =
		classes()
			.that(ClassUtils.areAnnotatedWith("ValueObject"))
			.should(ClassUtils.overrideObjectClassEqualsMethodAccessingAllFields)
			.as("Classes annotated with @ValueObject should override Object.class equals method accessing all fields");

	@ArchTest
	final public static ArchRule value_objects_must_override_object_class_hash_code_method_accessing_all_fields =
		classes()
			.that(ClassUtils.areAnnotatedWith("ValueObject"))
			.should(ClassUtils.overrideObjectClassHashCodeMethodAccessingAllFields)
			.as("Classes annotated with @ValueObject should override Object.class hashCode method accessing all fields");

	@ArchTest
	final public static ArchRule value_objects_must_override_object_class_to_string_method_accessing_all_fields =
		classes()
			.that(ClassUtils.areAnnotatedWith("ValueObject"))
			.should(ClassUtils.overrideObjectClassToStringMethodAccessingAllFields)
			.as("Classes annotated with @ValueObject should override Object.class toString method accessing all fields");

	@ArchTest
	final public static ArchRule value_objects_fields_must_be_private =
		fields()
			.that().areDeclaredInClassesThat(ClassUtils.areAnnotatedWith("ValueObject"))
			.should().bePrivate()
			.as("Classes annotated with @ValueObject fields should be private");

	@ArchTest
	final public static ArchRule value_objects_fields_must_have_private_named_setters =
		methods()
			.that().areDeclaredInClassesThat(ClassUtils.areAnnotatedWith("ValueObject"))
				.and(MethodUtils.accessAtLeastOneField)
				.and().haveNameMatching("set[a-zA-Z]*")
			.should().bePrivate()
			.as("Classes annotated with @ValueObject fields should have private setters");

	@ArchTest
	final public static ArchRule value_objects_fields_must_have_private_annotated_setters =
		methods()
			.that().areDeclaredInClassesThat(ClassUtils.areAnnotatedWith("ValueObject"))
				.and(MethodUtils.accessAtLeastOneField)
				.and(MethodUtils.areAnnotatedWith("Setter"))
			.should().bePrivate()
			.as("Classes annotated with @ValueObject fields should have private annotated setters");

	@ArchTest
	final public static ArchRule value_objects_fields_must_have_specific_named_getters =
		methods()
			.that().areDeclaredInClassesThat(ClassUtils.areAnnotatedWith("ValueObject"))
				.and(MethodUtils.accessAtLeastOneField)
				.and().haveNameMatching("get[a-zA-Z]*")
			.should(MethodUtils.accessOnlyImmutableFields)
				.orShould().bePrivate()
			.as("Classes annotated with @ValueObject fields should have specific getters");

	@ArchTest
	final public static ArchRule value_objects_fields_must_have_specific_annotated_getters =
		methods()
			.that().areDeclaredInClassesThat(ClassUtils.areAnnotatedWith("ValueObject"))
				.and(MethodUtils.accessAtLeastOneField)
				.and(MethodUtils.areAnnotatedWith("Getter"))
			.should(MethodUtils.accessOnlyImmutableFields)
				.orShould().bePrivate()
			.as("Classes annotated with @ValueObject fields should have specific annotated getters");

	@ArchTest
	final public static ArchRule value_objects_fields_must_have_private_accesses =
		methods()
			.that().areDeclaredInClassesThat(ClassUtils.areAnnotatedWith("ValueObject"))
				.and(MethodUtils.accessAtLeastOneField)
				.and(MethodUtils.areNotAnnotatedWith("Setter"))
				.and(MethodUtils.areNotAnnotatedWith("Getter"))
				.and(MethodUtils.areNotObjectClassMethods)
			.should(MethodUtils.accessOnlyImmutableFields)
				.orShould().bePrivate()
			.as("Classes annotated with @ValueObject fields should have private accesses");
}