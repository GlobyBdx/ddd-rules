package fr.ubordeaux.ddd.rules.tests;

import com.google.common.collect.Sets;
import com.tngtech.archunit.core.domain.JavaClasses;
import com.tngtech.archunit.core.importer.ClassFileImporter;
import com.tngtech.archunit.lang.ArchRule;

import fr.ubordeaux.ddd.example.domain.model.evil.EvilTokenWithMultipleAnnotations;
import fr.ubordeaux.ddd.example.domain.model.evil.factories.EvilFactoryWithoutEntityValueObjectConstructorAccess;
import fr.ubordeaux.ddd.example.domain.model.evil.factories.EvilFactoryWithoutMethod;
import fr.ubordeaux.ddd.example.domain.model.evil.factories.EvilFactoryWithoutPublicProtectedMethod;
import fr.ubordeaux.ddd.example.domain.model.evil.factories.EvilFactoryWithoutPublicProtectedMethodsWithSameReturnType;
import fr.ubordeaux.ddd.example.domain.model.good.factories.GoodFactory;
import fr.ubordeaux.ddd.example.evil.factories.EvilFactoryOutsideDomainLayer;
import fr.ubordeaux.ddd.rules.FactoryRules;
import fr.ubordeaux.ddd.rules.tests.utils.TestUtils;
import fr.ubordeaux.ddd.rules.utils.PackageUtils;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.assertj.core.api.Assertions.*;

import java.util.Set;

public class FactoryRulesTestBis {
	final private static JavaClasses classes = new ClassFileImporter().importPackages("fr.ubordeaux.ddd.example");

	final private Set<Class<?>> factories =
			Sets.newHashSet(
					EvilTokenWithMultipleAnnotations.class,
					EvilFactoryWithoutEntityValueObjectConstructorAccess.class,
					EvilFactoryWithoutMethod.class,
					EvilFactoryWithoutPublicProtectedMethod.class,
					EvilFactoryWithoutPublicProtectedMethodsWithSameReturnType.class,
					GoodFactory.class,
					EvilFactoryOutsideDomainLayer.class);

	final private Set<Class<?>> factoriesOutsideDomainLayer =
			Sets.newHashSet(
					EvilFactoryOutsideDomainLayer.class);

	final private Set<Class<?>> factoriesWithIllegalAnnotations =
			Sets.newHashSet(
					EvilTokenWithMultipleAnnotations.class);

	final private Set<Class<?>> factoriesWithoutEntityValueObjectConstructorAccess =
			Sets.newHashSet(
					EvilTokenWithMultipleAnnotations.class,
					EvilFactoryWithoutEntityValueObjectConstructorAccess.class,
					EvilFactoryWithoutMethod.class);

	final private Set<Class<?>> factoriesWithoutPublicProtectedMethodsWithSameReturnType =
			Sets.newHashSet(
					EvilTokenWithMultipleAnnotations.class,
					EvilFactoryWithoutMethod.class,
					EvilFactoryWithoutPublicProtectedMethod.class,
					EvilFactoryWithoutPublicProtectedMethodsWithSameReturnType.class);

	@BeforeClass
	public static void beforeAllTestMethods() {
		PackageUtils.importAllPackageInfoClasses(classes);
	}

	@AfterClass
	public static void afterAllTestMethods() {
		PackageUtils.clearAllPackageInfoClasses();
	}

	private void assertThatCodeExcept(ArchRule rule, Set<Class<?>> exceptions) {
		assertThatCode(() -> rule.check(TestUtils.computeClassesExcept(factories, exceptions))).doesNotThrowAnyException();
	}

	@Test
	public void shouldNotThrowAnyViolation() {
		assertThatCodeExcept(FactoryRules.factories_must_reside_in_a_package_named_domain, factoriesOutsideDomainLayer);
		assertThatCodeExcept(FactoryRules.factories_must_reside_in_a_package_annotated_with_domain, factoriesOutsideDomainLayer);

		assertThatCodeExcept(FactoryRules.factories_must_not_also_be_annotated_with_entity, factoriesWithIllegalAnnotations);
		assertThatCodeExcept(FactoryRules.factories_must_not_also_be_annotated_with_repository, factoriesWithIllegalAnnotations);
		assertThatCodeExcept(FactoryRules.factories_must_not_also_be_annotated_with_service, factoriesWithIllegalAnnotations);
		assertThatCodeExcept(FactoryRules.factories_must_not_also_be_annotated_with_value_object, factoriesWithIllegalAnnotations);

		assertThatCodeExcept(FactoryRules.factories_must_access_at_least_one_constructor_from_an_entity_or_a_value_object, factoriesWithoutEntityValueObjectConstructorAccess);

		assertThatCodeExcept(FactoryRules.factories_must_have_public_and_protected_methods_with_the_same_return_type, factoriesWithoutPublicProtectedMethodsWithSameReturnType);
	}

	@Test
	public void shouldThrowViolations() {
		catchThrowable(() -> {
			FactoryRules.factories_must_reside_in_a_package_named_domain.check(TestUtils.computeClasses(factoriesOutsideDomainLayer));
		});
		catchThrowable(() -> {
			FactoryRules.factories_must_reside_in_a_package_annotated_with_domain.check(TestUtils.computeClasses(factoriesOutsideDomainLayer));
		});

		catchThrowable(() -> {
			FactoryRules.factories_must_not_also_be_annotated_with_entity.check(TestUtils.computeClasses(factoriesWithIllegalAnnotations));
		});
		catchThrowable(() -> {
			FactoryRules.factories_must_not_also_be_annotated_with_repository.check(TestUtils.computeClasses(factoriesWithIllegalAnnotations));
		});
		catchThrowable(() -> {
			FactoryRules.factories_must_not_also_be_annotated_with_service.check(TestUtils.computeClasses(factoriesWithIllegalAnnotations));
		});
		catchThrowable(() -> {
			FactoryRules.factories_must_not_also_be_annotated_with_value_object.check(TestUtils.computeClasses(factoriesWithIllegalAnnotations));
		});

		catchThrowable(() -> {
			FactoryRules.factories_must_access_at_least_one_constructor_from_an_entity_or_a_value_object.check(TestUtils.computeClasses(factoriesWithoutEntityValueObjectConstructorAccess));
		});

		catchThrowable(() -> {
			FactoryRules.factories_must_have_public_and_protected_methods_with_the_same_return_type.check(TestUtils.computeClasses(factoriesWithoutPublicProtectedMethodsWithSameReturnType));
		});
	}
}