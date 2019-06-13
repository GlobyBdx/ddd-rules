package fr.ubordeaux.ddd.rules.tests;

import com.google.common.collect.Sets;
import com.tngtech.archunit.lang.ArchRule;

import fr.ubordeaux.ddd.example.domain.model.evil.EvilTokenWithMultipleAnnotations;
import fr.ubordeaux.ddd.example.domain.model.evil.aggregates.EvilAggregateWithExternallyAccessedInternalObjects;
import fr.ubordeaux.ddd.example.domain.model.evil.aggregates.EvilAggregateWithoutEntityAnnotation;
import fr.ubordeaux.ddd.example.domain.model.good.aggregates.GoodAggregate;
import fr.ubordeaux.ddd.example.evil.aggregates.EvilAggregateOutsideDomainLayer;
import fr.ubordeaux.ddd.rules.AggregateRules;
import fr.ubordeaux.ddd.rules.tests.utils.TestUtils;

import org.junit.Test;

import static org.assertj.core.api.Assertions.*;

import java.util.Set;

public class AggregateRulesTest {
	final private Set<Class<?>> aggregates =
			Sets.newHashSet(
					EvilTokenWithMultipleAnnotations.class,
					EvilAggregateWithExternallyAccessedInternalObjects.class,
					EvilAggregateWithoutEntityAnnotation.class,
					GoodAggregate.class,
					EvilAggregateOutsideDomainLayer.class);

	final private Set<Class<?>> aggregatesWithoutEntityAnnotation =
			Sets.newHashSet(
					EvilAggregateWithoutEntityAnnotation.class);

	final private Set<Class<?>> aggregatesWithExternallyAccessedInternalObjects =
			Sets.newHashSet(
					EvilAggregateWithExternallyAccessedInternalObjects.class,
					GoodAggregate.class);

	final private Set<Class<?>> aggregatesWithExternallyInstantiatedInternalObjects =
			Sets.newHashSet(
					EvilAggregateWithExternallyAccessedInternalObjects.class);

	private void assertThatCodeExcept(ArchRule rule, Set<Class<?>> exceptions) {
		assertThatCode(() -> rule.check(TestUtils.computeClassesExcept(aggregates, exceptions))).doesNotThrowAnyException();
	}

	@Test
	public void shouldNotThrowAnyViolation() {
		assertThatCodeExcept(AggregateRules.aggregates_must_also_be_annotated_with_entity, aggregatesWithoutEntityAnnotation);

		assertThatCodeExcept(AggregateRules.aggregates_internal_objects_must_only_be_externally_accessed_from_owner_aggregates, aggregatesWithExternallyAccessedInternalObjects);
		assertThatCodeExcept(AggregateRules.aggregates_internal_objects_must_only_be_externally_instantiated_by_owner_aggregates_specific_factories, aggregatesWithExternallyInstantiatedInternalObjects);
	}

	@Test
	public void shouldThrowViolations() {
		catchThrowable(() -> {
			AggregateRules.aggregates_must_also_be_annotated_with_entity.check(TestUtils.computeClasses(aggregatesWithoutEntityAnnotation));
		});

		catchThrowable(() -> {
			AggregateRules.aggregates_internal_objects_must_only_be_externally_accessed_from_owner_aggregates.check(TestUtils.computeClasses(aggregatesWithExternallyAccessedInternalObjects));
		});
		catchThrowable(() -> {
			AggregateRules.aggregates_internal_objects_must_only_be_externally_instantiated_by_owner_aggregates_specific_factories.check(TestUtils.computeClasses(aggregatesWithExternallyInstantiatedInternalObjects));
		});
	}
}