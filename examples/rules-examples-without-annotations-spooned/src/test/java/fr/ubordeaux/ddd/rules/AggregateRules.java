package fr.ubordeaux.ddd.rules;

import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.classes;
import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.fields;

import com.tngtech.archunit.junit.AnalyzeClasses;
import com.tngtech.archunit.junit.ArchTest;
import com.tngtech.archunit.junit.ArchUnitRunner;
import com.tngtech.archunit.lang.ArchRule;

import fr.ubordeaux.ddd.annotations.types.Aggregate;
import fr.ubordeaux.ddd.annotations.types.Entity;
import fr.ubordeaux.ddd.rules.utils.FieldUtils;

import org.junit.runner.RunWith;

@RunWith(ArchUnitRunner.class)
@AnalyzeClasses(packages = "fr.ubordeaux.ddd.example")

public class AggregateRules {
	@ArchTest
	final public static ArchRule aggregates_must_also_be_annotated_with_entity =
		classes()
			.that().areAnnotatedWith(Aggregate.class)
			.should().beAnnotatedWith(Entity.class)
			.as("Classes annotated with @" + Aggregate.class.getSimpleName() + " should also be annotated with @" + Entity.class.getSimpleName());

	@ArchTest
	final public static ArchRule aggregates_internal_objects_must_only_be_externally_accessed_from_owner_aggregates =
		fields()
			.that().areDeclaredInClassesThat().areAnnotatedWith(Aggregate.class)
				.and(FieldUtils.possessImmutability)
			.should(FieldUtils.notBeExternallyAccessed)
			.as("Classes annotated with @" + Aggregate.class.getSimpleName() + " internal objects should only be externally accessed from owner classes");

	@ArchTest
	final public static ArchRule aggregates_internal_objects_must_only_be_externally_instantiated_by_owner_aggregates_specific_factories =
		fields()
			.that().areDeclaredInClassesThat().areAnnotatedWith(Aggregate.class)
				.and(FieldUtils.possessImmutability)
			.should(FieldUtils.notBeExternallyInstantiated)
			.as("Classes annotated with @" + Aggregate.class.getSimpleName() + " internal objects should only be externally instantiated by owner classes specific factories");
}