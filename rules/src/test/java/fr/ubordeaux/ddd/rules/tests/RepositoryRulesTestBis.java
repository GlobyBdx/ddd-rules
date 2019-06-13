package fr.ubordeaux.ddd.rules.tests;

import com.google.common.collect.Sets;
import com.tngtech.archunit.core.domain.JavaClasses;
import com.tngtech.archunit.core.importer.ClassFileImporter;
import com.tngtech.archunit.lang.ArchRule;

import fr.ubordeaux.ddd.example.domain.model.evil.EvilTokenWithMultipleAnnotations;
import fr.ubordeaux.ddd.example.evil.repositories.EvilRepositoryImplementationOutsideInfrastructureLayer;
import fr.ubordeaux.ddd.example.infrastructure.evil.repositories.EvilRepositoryImplementationWithInterfaceOutsideDomainLayer;
import fr.ubordeaux.ddd.example.infrastructure.evil.repositories.EvilRepositoryImplementationWithoutEntityValueObjectAccess;
import fr.ubordeaux.ddd.example.infrastructure.evil.repositories.EvilRepositoryImplementationWithoutInterface;
import fr.ubordeaux.ddd.example.infrastructure.good.repositories.GoodRepositoryImplementation;
import fr.ubordeaux.ddd.rules.RepositoryRules;
import fr.ubordeaux.ddd.rules.tests.utils.TestUtils;
import fr.ubordeaux.ddd.rules.utils.PackageUtils;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.assertj.core.api.Assertions.*;

import java.util.Set;

public class RepositoryRulesTestBis {
	final private static JavaClasses classes = new ClassFileImporter().importPackages("fr.ubordeaux.ddd.example");

	final private Set<Class<?>> repositories =
			Sets.newHashSet(
					EvilTokenWithMultipleAnnotations.class,
					EvilRepositoryImplementationOutsideInfrastructureLayer.class,
					EvilRepositoryImplementationWithInterfaceOutsideDomainLayer.class,
					EvilRepositoryImplementationWithoutEntityValueObjectAccess.class,
					EvilRepositoryImplementationWithoutInterface.class,
					GoodRepositoryImplementation.class);

	final private Set<Class<?>> repositoriesOutsideInfrastructureLayer =
			Sets.newHashSet(
					EvilTokenWithMultipleAnnotations.class,
					EvilRepositoryImplementationOutsideInfrastructureLayer.class);

	final private Set<Class<?>> repositoriesWithIllegalAnnotations =
			Sets.newHashSet(
					EvilTokenWithMultipleAnnotations.class);

	final private Set<Class<?>> repositoriesWithInterfaceOutsideDomainLayer =
			Sets.newHashSet(
					EvilTokenWithMultipleAnnotations.class,
					EvilRepositoryImplementationWithInterfaceOutsideDomainLayer.class,
					EvilRepositoryImplementationWithoutInterface.class);

	final private Set<Class<?>> repositoriesWithoutEntityValueObjectAccess =
			Sets.newHashSet(
					EvilTokenWithMultipleAnnotations.class,
					EvilRepositoryImplementationWithoutEntityValueObjectAccess.class,
					EvilRepositoryImplementationWithoutInterface.class);

	@BeforeClass
	public static void beforeAllTestMethods() {
		PackageUtils.importAllPackageInfoClasses(classes);
	}

	@AfterClass
	public static void afterAllTestMethods() {
		PackageUtils.clearAllPackageInfoClasses();
	}

	private void assertThatCodeExcept(ArchRule rule, Set<Class<?>> exceptions) {
		assertThatCode(() -> rule.check(TestUtils.computeClassesExcept(repositories, exceptions))).doesNotThrowAnyException();
	}

	@Test
	public void shouldNotThrowAnyViolation() {
		assertThatCodeExcept(RepositoryRules.repositories_must_reside_in_a_package_named_infrastructure, repositoriesOutsideInfrastructureLayer);
		assertThatCodeExcept(RepositoryRules.repositories_must_reside_in_a_package_annotated_with_infrastructure, repositoriesOutsideInfrastructureLayer);

		assertThatCodeExcept(RepositoryRules.repositories_must_not_also_be_annotated_with_entity, repositoriesWithIllegalAnnotations);
		assertThatCodeExcept(RepositoryRules.repositories_must_not_also_be_annotated_with_factory, repositoriesWithIllegalAnnotations);
		assertThatCodeExcept(RepositoryRules.repositories_must_not_also_be_annotated_with_service, repositoriesWithIllegalAnnotations);
		assertThatCodeExcept(RepositoryRules.repositories_must_not_also_be_annotated_with_value_object, repositoriesWithIllegalAnnotations);

		assertThatCodeExcept(RepositoryRules.repositories_must_implement_an_interface_in_a_domain_layer, repositoriesWithInterfaceOutsideDomainLayer);

		assertThatCodeExcept(RepositoryRules.repositories_must_access_at_least_one_entity_or_value_object, repositoriesWithoutEntityValueObjectAccess);
	}

	@Test
	public void shouldThrowViolations() {
		catchThrowable(() -> {
			RepositoryRules.repositories_must_reside_in_a_package_named_infrastructure.check(TestUtils.computeClasses(repositoriesOutsideInfrastructureLayer));
		});
		catchThrowable(() -> {
			RepositoryRules.repositories_must_reside_in_a_package_annotated_with_infrastructure.check(TestUtils.computeClasses(repositoriesOutsideInfrastructureLayer));
		});

		catchThrowable(() -> {
			RepositoryRules.repositories_must_not_also_be_annotated_with_entity.check(TestUtils.computeClasses(repositoriesWithIllegalAnnotations));
		});
		catchThrowable(() -> {
			RepositoryRules.repositories_must_not_also_be_annotated_with_factory.check(TestUtils.computeClasses(repositoriesWithIllegalAnnotations));
		});
		catchThrowable(() -> {
			RepositoryRules.repositories_must_not_also_be_annotated_with_service.check(TestUtils.computeClasses(repositoriesWithIllegalAnnotations));
		});
		catchThrowable(() -> {
			RepositoryRules.repositories_must_not_also_be_annotated_with_value_object.check(TestUtils.computeClasses(repositoriesWithIllegalAnnotations));
		});

		catchThrowable(() -> {
			RepositoryRules.repositories_must_implement_an_interface_in_a_domain_layer.check(TestUtils.computeClasses(repositoriesWithInterfaceOutsideDomainLayer));
		});

		catchThrowable(() -> {
			RepositoryRules.repositories_must_access_at_least_one_entity_or_value_object.check(TestUtils.computeClasses(repositoriesWithoutEntityValueObjectAccess));
		});
	}
}