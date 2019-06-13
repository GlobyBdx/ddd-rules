package fr.ubordeaux.ddd.rules.tests;

import com.google.common.collect.Sets;
import com.tngtech.archunit.core.domain.JavaClasses;
import com.tngtech.archunit.core.importer.ClassFileImporter;
import com.tngtech.archunit.lang.ArchRule;

import fr.ubordeaux.ddd.example.domain.model.evil.EvilTokenWithMultipleAnnotations;
import fr.ubordeaux.ddd.example.domain.model.evil.valueobjects.EvilValueObjectWithAccessIssue;
import fr.ubordeaux.ddd.example.domain.model.evil.valueobjects.EvilValueObjectWithBadObjectClassMethodsA;
import fr.ubordeaux.ddd.example.domain.model.evil.valueobjects.EvilValueObjectWithBadObjectClassMethodsB;
import fr.ubordeaux.ddd.example.domain.model.evil.valueobjects.EvilValueObjectWithBadObjectClassMethodsC;
import fr.ubordeaux.ddd.example.domain.model.evil.valueobjects.EvilValueObjectWithGetterIssue;
import fr.ubordeaux.ddd.example.domain.model.evil.valueobjects.EvilValueObjectWithPublicFieldA;
import fr.ubordeaux.ddd.example.domain.model.evil.valueobjects.EvilValueObjectWithPublicFieldB;
import fr.ubordeaux.ddd.example.domain.model.evil.valueobjects.EvilValueObjectWithPublicFields;
import fr.ubordeaux.ddd.example.domain.model.evil.valueobjects.EvilValueObjectWithPublicSetterA;
import fr.ubordeaux.ddd.example.domain.model.evil.valueobjects.EvilValueObjectWithPublicSetterB;
import fr.ubordeaux.ddd.example.domain.model.evil.valueobjects.EvilValueObjectWithPublicSetters;
import fr.ubordeaux.ddd.example.domain.model.evil.valueobjects.EvilValueObjectWithoutField;
import fr.ubordeaux.ddd.example.domain.model.evil.valueobjects.EvilValueObjectWithoutObjectClassMethod;
import fr.ubordeaux.ddd.example.domain.model.evil.valueobjects.GoodValueObjectWithAccessIssue;
import fr.ubordeaux.ddd.example.domain.model.evil.valueobjects.GoodValueObjectWithBadObjectClassMethods;
import fr.ubordeaux.ddd.example.domain.model.evil.valueobjects.GoodValueObjectWithGetterIssue;
import fr.ubordeaux.ddd.example.domain.model.evil.valueobjects.GoodValueObjectWithImmutableAnnotation;
import fr.ubordeaux.ddd.example.domain.model.evil.valueobjects.GoodValueObjectWithPublicSetter;
import fr.ubordeaux.ddd.example.domain.model.good.valueobjects.GoodValueObjectA;
import fr.ubordeaux.ddd.example.domain.model.good.valueobjects.GoodValueObjectB;
import fr.ubordeaux.ddd.example.domain.model.good.valueobjects.GoodValueObjectC;
import fr.ubordeaux.ddd.example.domain.model.good.valueobjects.GoodValueObjectD;
import fr.ubordeaux.ddd.example.domain.model.good.valueobjects.GoodValueObjectE;
import fr.ubordeaux.ddd.example.domain.model.good.valueobjects.GoodValueObjectWithFinalPrimitive;
import fr.ubordeaux.ddd.example.evil.valueobjects.EvilValueObjectOutsideDomainLayer;
import fr.ubordeaux.ddd.rules.ValueObjectRules;
import fr.ubordeaux.ddd.rules.tests.utils.TestUtils;
import fr.ubordeaux.ddd.rules.utils.PackageUtils;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.assertj.core.api.Assertions.*;

import java.util.Set;

public class ValueObjectRulesTestBis {
	final private static JavaClasses classes = new ClassFileImporter().importPackages("fr.ubordeaux.ddd.example");

	final private Set<Class<?>> valueObjects =
			Sets.newHashSet(
					EvilTokenWithMultipleAnnotations.class,
					EvilValueObjectWithAccessIssue.class,
					EvilValueObjectWithBadObjectClassMethodsA.class,
					EvilValueObjectWithBadObjectClassMethodsB.class,
					EvilValueObjectWithBadObjectClassMethodsC.class,
					EvilValueObjectWithGetterIssue.class,
					EvilValueObjectWithoutField.class,
					EvilValueObjectWithoutObjectClassMethod.class,
					EvilValueObjectWithPublicFieldA.class,
					EvilValueObjectWithPublicFieldB.class,
					EvilValueObjectWithPublicFields.class,
					EvilValueObjectWithPublicSetterA.class,
					EvilValueObjectWithPublicSetterB.class,
					EvilValueObjectWithPublicSetters.class,
					GoodValueObjectWithAccessIssue.class,
					GoodValueObjectWithBadObjectClassMethods.class,
					GoodValueObjectWithGetterIssue.class,
					GoodValueObjectWithImmutableAnnotation.class,
					GoodValueObjectWithPublicSetter.class,
					GoodValueObjectA.class,
					GoodValueObjectB.class,
					GoodValueObjectC.class,
					GoodValueObjectD.class,
					GoodValueObjectE.class,
					GoodValueObjectWithFinalPrimitive.class,
					EvilValueObjectOutsideDomainLayer.class);

	final private Set<Class<?>> valueObjectsOutsideDomainLayer =
			Sets.newHashSet(
					EvilValueObjectOutsideDomainLayer.class);

	final private Set<Class<?>> valueObjectsWithIllegalAnnotations =
			Sets.newHashSet(
					EvilTokenWithMultipleAnnotations.class);

	final private Set<Class<?>> valueObjectsWithoutField =
			Sets.newHashSet(
					EvilTokenWithMultipleAnnotations.class,
					EvilValueObjectWithoutField.class);

	final private Set<Class<?>> valueObjectsWithBadObjectClassMethods =
			Sets.newHashSet(
					EvilTokenWithMultipleAnnotations.class,
					EvilValueObjectWithBadObjectClassMethodsA.class,
					EvilValueObjectWithBadObjectClassMethodsB.class,
					EvilValueObjectWithBadObjectClassMethodsC.class,
					EvilValueObjectWithoutObjectClassMethod.class);

	final private Set<Class<?>> valueObjectsWithPublicFields =
			Sets.newHashSet(
					EvilValueObjectWithPublicFieldA.class,
					EvilValueObjectWithPublicFieldB.class,
					EvilValueObjectWithPublicFields.class);

	final private Set<Class<?>> valueObjectsWithPublicSetters =
			Sets.newHashSet(
					EvilValueObjectWithPublicSetterA.class,
					EvilValueObjectWithPublicSetterB.class,
					EvilValueObjectWithPublicSetters.class);

	final private Set<Class<?>> valueObjectsWithGetterIssues =
			Sets.newHashSet(
					EvilValueObjectWithGetterIssue.class);

	final private Set<Class<?>> valueObjectsWithAccessIssues =
			Sets.newHashSet(
					EvilValueObjectWithAccessIssue.class,
					EvilValueObjectWithBadObjectClassMethodsA.class);

	@BeforeClass
	public static void beforeAllTestMethods() {
		PackageUtils.importAllPackageInfoClasses(classes);
	}

	@AfterClass
	public static void afterAllTestMethods() {
		PackageUtils.clearAllPackageInfoClasses();
	}

	private void assertThatCodeExcept(ArchRule rule, Set<Class<?>> exceptions) {
		assertThatCode(() -> rule.check(TestUtils.computeClassesExcept(valueObjects, exceptions))).doesNotThrowAnyException();
	}

	@Test
	public void shouldNotThrowAnyViolation() {
		assertThatCodeExcept(ValueObjectRules.value_objects_must_reside_in_a_package_named_domain, valueObjectsOutsideDomainLayer);
		assertThatCodeExcept(ValueObjectRules.value_objects_must_reside_in_a_package_annotated_with_domain, valueObjectsOutsideDomainLayer);

		assertThatCodeExcept(ValueObjectRules.value_objects_must_not_also_be_annotated_with_entity, valueObjectsWithIllegalAnnotations);
		assertThatCodeExcept(ValueObjectRules.value_objects_must_not_also_be_annotated_with_factory, valueObjectsWithIllegalAnnotations);
		assertThatCodeExcept(ValueObjectRules.value_objects_must_not_also_be_annotated_with_repository, valueObjectsWithIllegalAnnotations);
		assertThatCodeExcept(ValueObjectRules.value_objects_must_not_also_be_annotated_with_service, valueObjectsWithIllegalAnnotations);

		assertThatCodeExcept(ValueObjectRules.value_objects_must_have_at_least_one_field, valueObjectsWithoutField);

		assertThatCodeExcept(ValueObjectRules.value_objects_must_override_object_class_equals_method_accessing_all_fields, valueObjectsWithBadObjectClassMethods);
		assertThatCodeExcept(ValueObjectRules.value_objects_must_override_object_class_hash_code_method_accessing_all_fields, valueObjectsWithBadObjectClassMethods);
		assertThatCodeExcept(ValueObjectRules.value_objects_must_override_object_class_to_string_method_accessing_all_fields, valueObjectsWithBadObjectClassMethods);

		assertThatCodeExcept(ValueObjectRules.value_objects_fields_must_be_private, valueObjectsWithPublicFields);

		assertThatCodeExcept(ValueObjectRules.value_objects_fields_must_have_private_named_setters, valueObjectsWithPublicSetters);
		assertThatCodeExcept(ValueObjectRules.value_objects_fields_must_have_private_annotated_setters, valueObjectsWithPublicSetters);
		assertThatCodeExcept(ValueObjectRules.value_objects_fields_must_have_specific_named_getters, valueObjectsWithGetterIssues);
		assertThatCodeExcept(ValueObjectRules.value_objects_fields_must_have_specific_annotated_getters, valueObjectsWithGetterIssues);
		assertThatCodeExcept(ValueObjectRules.value_objects_fields_must_have_private_accesses, valueObjectsWithAccessIssues);
	}

	@Test
	public void shouldThrowViolations() {
		catchThrowable(() -> {
			ValueObjectRules.value_objects_must_reside_in_a_package_named_domain.check(TestUtils.computeClasses(valueObjectsOutsideDomainLayer));
		});
		catchThrowable(() -> {
			ValueObjectRules.value_objects_must_reside_in_a_package_annotated_with_domain.check(TestUtils.computeClasses(valueObjectsOutsideDomainLayer));
		});

		catchThrowable(() -> {
			ValueObjectRules.value_objects_must_not_also_be_annotated_with_entity.check(TestUtils.computeClasses(valueObjectsWithIllegalAnnotations));
		});
		catchThrowable(() -> {
			ValueObjectRules.value_objects_must_not_also_be_annotated_with_factory.check(TestUtils.computeClasses(valueObjectsWithIllegalAnnotations));
		});
		catchThrowable(() -> {
			ValueObjectRules.value_objects_must_not_also_be_annotated_with_repository.check(TestUtils.computeClasses(valueObjectsWithIllegalAnnotations));
		});
		catchThrowable(() -> {
			ValueObjectRules.value_objects_must_not_also_be_annotated_with_service.check(TestUtils.computeClasses(valueObjectsWithIllegalAnnotations));
		});

		catchThrowable(() -> {
			ValueObjectRules.value_objects_must_have_at_least_one_field.check(TestUtils.computeClasses(valueObjectsWithoutField));
		});

		catchThrowable(() -> {
			ValueObjectRules.value_objects_must_override_object_class_equals_method_accessing_all_fields.check(TestUtils.computeClasses(valueObjectsWithBadObjectClassMethods));
		});
		catchThrowable(() -> {
			ValueObjectRules.value_objects_must_override_object_class_hash_code_method_accessing_all_fields.check(TestUtils.computeClasses(valueObjectsWithBadObjectClassMethods));
		});
		catchThrowable(() -> {
			ValueObjectRules.value_objects_must_override_object_class_to_string_method_accessing_all_fields.check(TestUtils.computeClasses(valueObjectsWithBadObjectClassMethods));
		});

		catchThrowable(() -> {
			ValueObjectRules.value_objects_fields_must_be_private.check(TestUtils.computeClasses(valueObjectsWithPublicFields));
		});

		catchThrowable(() -> {
			ValueObjectRules.value_objects_fields_must_have_private_named_setters.check(TestUtils.computeClasses(valueObjectsWithPublicSetters));
		});
		catchThrowable(() -> {
			ValueObjectRules.value_objects_fields_must_have_private_annotated_setters.check(TestUtils.computeClasses(valueObjectsWithPublicSetters));
		});
		catchThrowable(() -> {
			ValueObjectRules.value_objects_fields_must_have_specific_named_getters.check(TestUtils.computeClasses(valueObjectsWithGetterIssues));
		});
		catchThrowable(() -> {
			ValueObjectRules.value_objects_fields_must_have_specific_annotated_getters.check(TestUtils.computeClasses(valueObjectsWithGetterIssues));
		});
		catchThrowable(() -> {
			ValueObjectRules.value_objects_fields_must_have_private_accesses.check(TestUtils.computeClasses(valueObjectsWithAccessIssues));
		});
	}
}