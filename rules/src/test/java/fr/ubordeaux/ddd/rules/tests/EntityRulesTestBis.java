package fr.ubordeaux.ddd.rules.tests;

import com.google.common.collect.Sets;
import com.tngtech.archunit.core.domain.JavaClasses;
import com.tngtech.archunit.core.importer.ClassFileImporter;
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
import fr.ubordeaux.ddd.rules.EntityRules;
import fr.ubordeaux.ddd.rules.tests.utils.TestUtils;
import fr.ubordeaux.ddd.rules.utils.PackageUtils;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.assertj.core.api.Assertions.*;

import java.util.Set;

public class EntityRulesTestBis {
	final private static JavaClasses classes = new ClassFileImporter().importPackages("fr.ubordeaux.ddd.example");

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

	final private Set<Class<?>> entitiesOutsideDomainLayer =
			Sets.newHashSet(
					EvilAggregateOutsideDomainLayer.class,
					EvilEntityOutsideDomainLayer.class);

	final private Set<Class<?>> entitiesWithIllegalAnnotations =
			Sets.newHashSet(
					EvilTokenWithMultipleAnnotations.class);

	final private Set<Class<?>> entitiesWithoutEntityIdAnnotation =
			Sets.newHashSet(
					EvilTokenWithMultipleAnnotations.class,
					EvilEntityWithoutEntityIdAnnotation.class,
					EvilEntityWithoutField.class);

	final private Set<Class<?>> entitiesWithBadObjectClassMethods =
			Sets.newHashSet(
					EvilTokenWithMultipleAnnotations.class,
					EvilEntityWithBadObjectClassMethodsA.class,
					EvilEntityWithBadObjectClassMethodsB.class,
					EvilEntityWithoutObjectClassMethod.class);

	@BeforeClass
	public static void beforeAllTestMethods() {
		PackageUtils.importAllPackageInfoClasses(classes);
	}

	@AfterClass
	public static void afterAllTestMethods() {
		PackageUtils.clearAllPackageInfoClasses();
	}

	private void assertThatCodeExcept(ArchRule rule, Set<Class<?>> exceptions) {
		assertThatCode(() -> rule.check(TestUtils.computeClassesExcept(entities, exceptions))).doesNotThrowAnyException();
	}

	@Test
	public void shouldNotThrowAnyViolation() {
		assertThatCodeExcept(EntityRules.entities_must_reside_in_a_package_named_domain, entitiesOutsideDomainLayer);
		assertThatCodeExcept(EntityRules.entities_must_reside_in_a_package_annotated_with_domain, entitiesOutsideDomainLayer);

		assertThatCodeExcept(EntityRules.entities_must_not_also_be_annotated_with_factory, entitiesWithIllegalAnnotations);
		assertThatCodeExcept(EntityRules.entities_must_not_also_be_annotated_with_repository, entitiesWithIllegalAnnotations);
		assertThatCodeExcept(EntityRules.entities_must_not_also_be_annotated_with_service, entitiesWithIllegalAnnotations);
		assertThatCodeExcept(EntityRules.entities_must_not_also_be_annotated_with_value_object, entitiesWithIllegalAnnotations);

		assertThatCodeExcept(EntityRules.entities_must_have_at_least_one_entity_id, entitiesWithoutEntityIdAnnotation);

		assertThatCodeExcept(EntityRules.entities_must_override_object_class_equals_method_accessing_all_entity_ids, entitiesWithBadObjectClassMethods);
		assertThatCodeExcept(EntityRules.entities_must_override_object_class_hash_code_method_accessing_all_entity_ids, entitiesWithBadObjectClassMethods);
		assertThatCodeExcept(EntityRules.entities_must_override_object_class_to_string_method_accessing_all_entity_ids, entitiesWithBadObjectClassMethods);
	}

	@Test
	public void shouldThrowViolations() {
		catchThrowable(() -> {
			EntityRules.entities_must_reside_in_a_package_named_domain.check(TestUtils.computeClasses(entitiesOutsideDomainLayer));
		});
		catchThrowable(() -> {
			EntityRules.entities_must_reside_in_a_package_annotated_with_domain.check(TestUtils.computeClasses(entitiesOutsideDomainLayer));
		});

		catchThrowable(() -> {
			EntityRules.entities_must_not_also_be_annotated_with_factory.check(TestUtils.computeClasses(entitiesWithIllegalAnnotations));
		});
		catchThrowable(() -> {
			EntityRules.entities_must_not_also_be_annotated_with_repository.check(TestUtils.computeClasses(entitiesWithIllegalAnnotations));
		});
		catchThrowable(() -> {
			EntityRules.entities_must_not_also_be_annotated_with_service.check(TestUtils.computeClasses(entitiesWithIllegalAnnotations));
		});
		catchThrowable(() -> {
			EntityRules.entities_must_not_also_be_annotated_with_value_object.check(TestUtils.computeClasses(entitiesWithIllegalAnnotations));
		});

		catchThrowable(() -> {
			EntityRules.entities_must_have_at_least_one_entity_id.check(TestUtils.computeClasses(entitiesWithoutEntityIdAnnotation));
		});

		catchThrowable(() -> {
			EntityRules.entities_must_override_object_class_equals_method_accessing_all_entity_ids.check(TestUtils.computeClasses(entitiesWithBadObjectClassMethods));
		});
		catchThrowable(() -> {
			EntityRules.entities_must_override_object_class_hash_code_method_accessing_all_entity_ids.check(TestUtils.computeClasses(entitiesWithBadObjectClassMethods));
		});
		catchThrowable(() -> {
			EntityRules.entities_must_override_object_class_to_string_method_accessing_all_entity_ids.check(TestUtils.computeClasses(entitiesWithBadObjectClassMethods));
		});
	}
}