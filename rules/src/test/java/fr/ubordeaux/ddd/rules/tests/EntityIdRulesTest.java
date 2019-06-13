package fr.ubordeaux.ddd.rules.tests;

import com.google.common.collect.Sets;
import com.tngtech.archunit.lang.ArchRule;

import fr.ubordeaux.ddd.example.domain.model.evil.EvilTokenWithMultipleAnnotations;
import fr.ubordeaux.ddd.example.domain.model.evil.aggregates.EvilAggregateWithExternallyAccessedInternalObjects;
import fr.ubordeaux.ddd.example.domain.model.evil.aggregates.EvilAggregateWithoutEntityAnnotation;
import fr.ubordeaux.ddd.example.domain.model.evil.entities.EvilEntityWithAccessIssue;
import fr.ubordeaux.ddd.example.domain.model.evil.entities.EvilEntityWithBadObjectClassMethodsA;
import fr.ubordeaux.ddd.example.domain.model.evil.entities.EvilEntityWithBadObjectClassMethodsB;
import fr.ubordeaux.ddd.example.domain.model.evil.entities.EvilEntityWithGetterIssue;
import fr.ubordeaux.ddd.example.domain.model.evil.entities.EvilEntityWithPublicEntityIdAnnotation;
import fr.ubordeaux.ddd.example.domain.model.evil.entities.EvilEntityWithPublicEntityIdSetter;
import fr.ubordeaux.ddd.example.domain.model.evil.entities.EvilEntityWithoutEntityAnnotation;
import fr.ubordeaux.ddd.example.domain.model.evil.entities.EvilEntityWithoutEntityIdAnnotation;
import fr.ubordeaux.ddd.example.domain.model.evil.entities.EvilEntityWithoutField;
import fr.ubordeaux.ddd.example.domain.model.evil.entities.EvilEntityWithoutObjectClassMethod;
import fr.ubordeaux.ddd.example.domain.model.evil.entities.GoodEntityWithAccessIssue;
import fr.ubordeaux.ddd.example.domain.model.evil.entities.GoodEntityWithBadObjectClassMethods;
import fr.ubordeaux.ddd.example.domain.model.evil.entities.GoodEntityWithGetterIssue;
import fr.ubordeaux.ddd.example.domain.model.evil.entities.GoodEntityWithImmutableAnnotation;
import fr.ubordeaux.ddd.example.domain.model.evil.entities.GoodEntityWithPublicEntityIdSetter;
import fr.ubordeaux.ddd.example.domain.model.good.aggregates.GoodAggregate;
import fr.ubordeaux.ddd.example.domain.model.good.entities.GoodEntityA;
import fr.ubordeaux.ddd.example.domain.model.good.entities.GoodEntityB;
import fr.ubordeaux.ddd.example.domain.model.good.entities.GoodEntityC;
import fr.ubordeaux.ddd.example.domain.model.good.entities.GoodEntityWithFinalPrimitive;
import fr.ubordeaux.ddd.example.evil.aggregates.EvilAggregateOutsideDomainLayer;
import fr.ubordeaux.ddd.example.evil.entities.EvilEntityOutsideDomainLayer;
import fr.ubordeaux.ddd.rules.EntityIdRules;
import fr.ubordeaux.ddd.rules.tests.utils.TestUtils;

import org.junit.Test;

import static org.assertj.core.api.Assertions.*;

import java.util.Set;

public class EntityIdRulesTest {
	final private Set<Class<?>> entities =
			Sets.newHashSet(
					EvilTokenWithMultipleAnnotations.class,
					EvilAggregateWithExternallyAccessedInternalObjects.class,
					EvilAggregateWithoutEntityAnnotation.class,
					EvilEntityWithAccessIssue.class,
					EvilEntityWithBadObjectClassMethodsA.class,
					EvilEntityWithBadObjectClassMethodsB.class,
					EvilEntityWithGetterIssue.class,
					EvilEntityWithoutEntityAnnotation.class,
					EvilEntityWithoutEntityIdAnnotation.class,
					EvilEntityWithoutField.class,
					EvilEntityWithoutObjectClassMethod.class,
					EvilEntityWithPublicEntityIdAnnotation.class,
					EvilEntityWithPublicEntityIdSetter.class,
					GoodEntityWithAccessIssue.class,
					GoodEntityWithBadObjectClassMethods.class,
					GoodEntityWithGetterIssue.class,
					GoodEntityWithImmutableAnnotation.class,
					GoodEntityWithPublicEntityIdSetter.class,
					GoodAggregate.class,
					GoodEntityA.class,
					GoodEntityB.class,
					GoodEntityC.class,
					GoodEntityWithFinalPrimitive.class,
					EvilAggregateOutsideDomainLayer.class,
					EvilEntityOutsideDomainLayer.class);

	final private Set<Class<?>> entitiesWithoutEntityAnnotation =
			Sets.newHashSet(
					EvilAggregateWithoutEntityAnnotation.class,
					EvilEntityWithoutEntityAnnotation.class);

	final private Set<Class<?>> entitiesWithPublicEntityIdAnnotations =
			Sets.newHashSet(
					EvilEntityWithPublicEntityIdAnnotation.class);

	final private Set<Class<?>> entitiesWithPublicEntityIdSetters =
			Sets.newHashSet(
					EvilEntityWithPublicEntityIdSetter.class);

	final private Set<Class<?>> entitiesWithGetterIssues =
			Sets.newHashSet(
					EvilEntityWithGetterIssue.class);

	final private Set<Class<?>> entitiesWithAccessIssues =
			Sets.newHashSet(
					EvilEntityWithAccessIssue.class,
					EvilEntityWithBadObjectClassMethodsA.class);

	private void assertThatCodeExcept(ArchRule rule, Set<Class<?>> exceptions) {
		assertThatCode(() -> rule.check(TestUtils.computeClassesExcept(entities, exceptions))).doesNotThrowAnyException();
	}

	@Test
	public void shouldNotThrowAnyViolation() {
		assertThatCodeExcept(EntityIdRules.entity_ids_must_only_be_used_by_entities, entitiesWithoutEntityAnnotation);

		assertThatCodeExcept(EntityIdRules.entity_ids_must_be_private, entitiesWithPublicEntityIdAnnotations);

		assertThatCodeExcept(EntityIdRules.entity_ids_must_have_private_named_setters, entitiesWithPublicEntityIdSetters);
		assertThatCodeExcept(EntityIdRules.entity_ids_must_have_private_annotated_setters, entitiesWithPublicEntityIdSetters);
		assertThatCodeExcept(EntityIdRules.entity_ids_must_have_specific_named_getters, entitiesWithGetterIssues);
		assertThatCodeExcept(EntityIdRules.entity_ids_must_have_specific_annotated_getters, entitiesWithGetterIssues);
		assertThatCodeExcept(EntityIdRules.entity_ids_must_have_private_accesses, entitiesWithAccessIssues);
	}

	@Test
	public void shouldThrowViolations() {
		catchThrowable(() -> {
			EntityIdRules.entity_ids_must_only_be_used_by_entities.check(TestUtils.computeClasses(entitiesWithoutEntityAnnotation));
		});

		catchThrowable(() -> {
			EntityIdRules.entity_ids_must_be_private.check(TestUtils.computeClasses(entitiesWithPublicEntityIdAnnotations));
		});

		catchThrowable(() -> {
			EntityIdRules.entity_ids_must_have_private_named_setters.check(TestUtils.computeClasses(entitiesWithPublicEntityIdSetters));
		});
		catchThrowable(() -> {
			EntityIdRules.entity_ids_must_have_private_annotated_setters.check(TestUtils.computeClasses(entitiesWithPublicEntityIdSetters));
		});
		catchThrowable(() -> {
			EntityIdRules.entity_ids_must_have_specific_named_getters.check(TestUtils.computeClasses(entitiesWithGetterIssues));
		});
		catchThrowable(() -> {
			EntityIdRules.entity_ids_must_have_specific_annotated_getters.check(TestUtils.computeClasses(entitiesWithGetterIssues));
		});
		catchThrowable(() -> {
			EntityIdRules.entity_ids_must_have_private_accesses.check(TestUtils.computeClasses(entitiesWithAccessIssues));
		});
	}
}