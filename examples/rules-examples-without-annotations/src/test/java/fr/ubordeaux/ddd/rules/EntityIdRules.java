package fr.ubordeaux.ddd.rules;

import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.fields;
import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.methods;

import com.tngtech.archunit.junit.AnalyzeClasses;
import com.tngtech.archunit.junit.ArchTest;
import com.tngtech.archunit.junit.ArchUnitRunner;
import com.tngtech.archunit.lang.ArchRule;

import fr.ubordeaux.ddd.rules.utils.ClassUtils;
import fr.ubordeaux.ddd.rules.utils.FieldUtils;
import fr.ubordeaux.ddd.rules.utils.MethodUtils;

import org.junit.runner.RunWith;

@RunWith(ArchUnitRunner.class)
@AnalyzeClasses(packages = "fr.ubordeaux.ddd.example")

public class EntityIdRules {
	@ArchTest
	final public static ArchRule entity_ids_must_only_be_used_by_entities =
		fields()
			.that(FieldUtils.areAnnotatedWith("EntityID"))
			.should().beDeclaredInClassesThat(ClassUtils.areAnnotatedWith("Entity"))
			.as("Fields annotated with @EntityID should only be used by classes annotated with @Entity");

	@ArchTest
	final public static ArchRule entity_ids_must_be_private =
		fields()
		.that(FieldUtils.areAnnotatedWith("EntityID"))
			.should().bePrivate()
			.as("Fields annotated with @EntityID should be private");

	@ArchTest
	final public static ArchRule entity_ids_must_have_private_named_setters =
		methods()
			.that(MethodUtils.accessAtLeastOneFieldAnnotatedWith("EntityID"))
				.and().haveNameMatching("set[a-zA-Z]*")
			.should().bePrivate()
			.as("Fields annotated with @EntityID should have private setters");

	@ArchTest
	final public static ArchRule entity_ids_must_have_private_annotated_setters =
		methods()
			.that(MethodUtils.accessAtLeastOneFieldAnnotatedWith("EntityID"))
				.and(MethodUtils.areAnnotatedWith("Setter"))
			.should().bePrivate()
			.as("Fields annotated with @EntityID should have private annotated setters");

	@ArchTest
	final public static ArchRule entity_ids_must_have_specific_named_getters =
		methods()
			.that(MethodUtils.accessAtLeastOneFieldAnnotatedWith("EntityID"))
			.and().haveNameMatching("get[a-zA-Z]*")
			.should(MethodUtils.accessOnlyImmutableFieldsAnnotatedWith("EntityID"))
				.orShould().bePrivate()
			.as("Fields annotated with @EntityID should have specific getters");

	@ArchTest
	final public static ArchRule entity_ids_must_have_specific_annotated_getters =
		methods()
			.that(MethodUtils.accessAtLeastOneFieldAnnotatedWith("EntityID"))
				.and(MethodUtils.areAnnotatedWith("Getter"))
			.should(MethodUtils.accessOnlyImmutableFieldsAnnotatedWith("EntityID"))
				.orShould().bePrivate()
			.as("Fields annotated with @EntityID should have specific annotated getters");

	@ArchTest
	final public static ArchRule entity_ids_must_have_private_accesses =
		methods()
			.that(MethodUtils.accessAtLeastOneFieldAnnotatedWith("EntityID"))
				.and(MethodUtils.areNotAnnotatedWith("Setter"))
				.and(MethodUtils.areNotAnnotatedWith("Getter"))
				.and(MethodUtils.areNotObjectClassMethods)
			.should(MethodUtils.accessOnlyImmutableFieldsAnnotatedWith("EntityID"))
				.orShould().bePrivate()
			.as("Fields annotated with @EntityID should have private accesses");
}