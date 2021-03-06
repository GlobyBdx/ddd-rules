package fr.ubordeaux.ddd.rules;

import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.fields;
import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.methods;

import com.tngtech.archunit.junit.AnalyzeClasses;
import com.tngtech.archunit.junit.ArchTest;
import com.tngtech.archunit.junit.ArchUnitRunner;
import com.tngtech.archunit.lang.ArchRule;

import fr.ubordeaux.ddd.annotations.fields.EntityID;
import fr.ubordeaux.ddd.annotations.methods.Getter;
import fr.ubordeaux.ddd.annotations.methods.Setter;
import fr.ubordeaux.ddd.annotations.types.Entity;
import fr.ubordeaux.ddd.rules.utils.MethodUtils;

import org.junit.runner.RunWith;

@RunWith(ArchUnitRunner.class)
@AnalyzeClasses(packages = "fr.ubordeaux.ddd.example")

public class EntityIdRules {
	@ArchTest
	final public static ArchRule entity_ids_must_only_be_used_by_entities =
		fields()
			.that().areAnnotatedWith(EntityID.class)
			.should().beDeclaredInClassesThat().areAnnotatedWith(Entity.class)
			.as("Fields annotated with @" + EntityID.class.getSimpleName() + " should only be used by classes annotated with @" + Entity.class.getSimpleName());

	@ArchTest
	final public static ArchRule entity_ids_must_be_private =
		fields()
			.that().areAnnotatedWith(EntityID.class)
			.should().bePrivate()
			.as("Fields annotated with @" + EntityID.class.getSimpleName() + " should be private");

	@ArchTest
	final public static ArchRule entity_ids_must_have_private_named_setters =
		methods()
			.that(MethodUtils.accessAtLeastOneFieldAnnotatedWith(EntityID.class))
				.and().haveNameMatching("set[a-zA-Z]*")
			.should().bePrivate()
			.as("Fields annotated with @" + EntityID.class.getSimpleName() + " should have private setters");

	@ArchTest
	final public static ArchRule entity_ids_must_have_private_annotated_setters =
		methods()
			.that(MethodUtils.accessAtLeastOneFieldAnnotatedWith(EntityID.class))
				.and().areAnnotatedWith(Setter.class)
			.should().bePrivate()
			.as("Fields annotated with @" + EntityID.class.getSimpleName() + " should have private annotated setters");

	@ArchTest
	final public static ArchRule entity_ids_must_have_specific_named_getters =
		methods()
			.that(MethodUtils.accessAtLeastOneFieldAnnotatedWith(EntityID.class))
			.and().haveNameMatching("get[a-zA-Z]*")
			.should(MethodUtils.accessOnlyImmutableFieldsAnnotatedWith(EntityID.class))
				.orShould().bePrivate()
			.as("Fields annotated with @" + EntityID.class.getSimpleName() + " should have specific getters");

	@ArchTest
	final public static ArchRule entity_ids_must_have_specific_annotated_getters =
		methods()
			.that(MethodUtils.accessAtLeastOneFieldAnnotatedWith(EntityID.class))
				.and().areAnnotatedWith(Getter.class)
			.should(MethodUtils.accessOnlyImmutableFieldsAnnotatedWith(EntityID.class))
				.orShould().bePrivate()
			.as("Fields annotated with @" + EntityID.class.getSimpleName() + " should have specific annotated getters");

	@ArchTest
	final public static ArchRule entity_ids_must_have_private_accesses =
		methods()
			.that(MethodUtils.accessAtLeastOneFieldAnnotatedWith(EntityID.class))
				.and().areNotAnnotatedWith(Setter.class)
				.and().areNotAnnotatedWith(Getter.class)
				.and(MethodUtils.areNotObjectClassMethods)
			.should(MethodUtils.accessOnlyImmutableFieldsAnnotatedWith(EntityID.class))
				.orShould().bePrivate()
			.as("Fields annotated with @" + EntityID.class.getSimpleName() + " should have private accesses");
}