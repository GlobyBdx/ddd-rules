package fr.ubordeaux.ddd.rules.tests;

import com.google.common.collect.Sets;
import com.tngtech.archunit.lang.ArchRule;

import fr.ubordeaux.ddd.example.application.evil.services.EvilServiceImplementationWithInterfaceOutsideSameLayer;
import fr.ubordeaux.ddd.example.application.evil.services.EvilServiceImplementationWithoutFinalField;
import fr.ubordeaux.ddd.example.application.evil.services.EvilServiceImplementationWithoutInterface;
import fr.ubordeaux.ddd.example.application.good.services.GoodServiceImplementationA;
import fr.ubordeaux.ddd.example.domain.model.evil.EvilTokenWithMultipleAnnotations;
import fr.ubordeaux.ddd.example.domain.model.good.services.GoodServiceImplementationB;
import fr.ubordeaux.ddd.example.evil.services.EvilServiceImplementationOutsideAnyLayer;
import fr.ubordeaux.ddd.example.infrastructure.good.services.GoodServiceImplementationC;
import fr.ubordeaux.ddd.rules.ServiceRules;
import fr.ubordeaux.ddd.rules.tests.utils.TestUtils;
import org.junit.Test;

import static org.assertj.core.api.Assertions.*;

import java.util.Set;

public class ServiceRulesTest {
	final private Set<Class<?>> services =
			Sets.newHashSet(
					EvilServiceImplementationWithInterfaceOutsideSameLayer.class,
					EvilServiceImplementationWithoutFinalField.class,
					EvilServiceImplementationWithoutInterface.class,
					GoodServiceImplementationA.class,
					EvilTokenWithMultipleAnnotations.class,
					GoodServiceImplementationB.class,
					EvilServiceImplementationOutsideAnyLayer.class,
					GoodServiceImplementationC.class);

	final private Set<Class<?>> servicesOutsideLegalLayers =
			Sets.newHashSet(
					EvilServiceImplementationOutsideAnyLayer.class);

	final private Set<Class<?>> servicesWithIllegalAnnotations =
			Sets.newHashSet(
					EvilTokenWithMultipleAnnotations.class);

	final private Set<Class<?>> servicesWithInterfaceOutsideSameLayer =
			Sets.newHashSet(
					EvilServiceImplementationWithInterfaceOutsideSameLayer.class,
					EvilServiceImplementationWithoutInterface.class,
					EvilTokenWithMultipleAnnotations.class,
					EvilServiceImplementationOutsideAnyLayer.class);

	final private Set<Class<?>> servicesWithoutFinalField =
			Sets.newHashSet(
					EvilServiceImplementationWithoutFinalField.class);

	private void assertThatCodeExcept(ArchRule rule, Set<Class<?>> exceptions) {
		assertThatCode(() -> rule.check(TestUtils.computeClassesExcept(services, exceptions))).doesNotThrowAnyException();
	}

	@Test
	public void shouldNotThrowAnyViolation() {
		assertThatCodeExcept(ServiceRules.services_must_reside_in_a_package_named_application_or_domain_or_infrastructure, servicesOutsideLegalLayers);
		assertThatCodeExcept(ServiceRules.services_must_reside_in_a_package_annotated_with_application_or_domain_or_infrastructure, servicesOutsideLegalLayers);

		assertThatCodeExcept(ServiceRules.services_must_not_also_be_annotated_with_entity, servicesWithIllegalAnnotations);
		assertThatCodeExcept(ServiceRules.services_must_not_also_be_annotated_with_factory, servicesWithIllegalAnnotations);
		assertThatCodeExcept(ServiceRules.services_must_not_also_be_annotated_with_repository, servicesWithIllegalAnnotations);
		assertThatCodeExcept(ServiceRules.services_must_not_also_be_annotated_with_value_object, servicesWithIllegalAnnotations);

		assertThatCodeExcept(ServiceRules.services_must_implement_an_interface_in_the_same_layer, servicesWithInterfaceOutsideSameLayer);

		assertThatCodeExcept(ServiceRules.services_fields_must_be_final, servicesWithoutFinalField);
	}

	@Test
	public void shouldThrowViolations() {
		catchThrowable(() -> {
			ServiceRules.services_must_reside_in_a_package_named_application_or_domain_or_infrastructure.check(TestUtils.computeClasses(servicesOutsideLegalLayers));
		});
		catchThrowable(() -> {
			ServiceRules.services_must_reside_in_a_package_annotated_with_application_or_domain_or_infrastructure.check(TestUtils.computeClasses(servicesOutsideLegalLayers));
		});

		catchThrowable(() -> {
			ServiceRules.services_must_not_also_be_annotated_with_entity.check(TestUtils.computeClasses(servicesWithIllegalAnnotations));
		});
		catchThrowable(() -> {
			ServiceRules.services_must_not_also_be_annotated_with_factory.check(TestUtils.computeClasses(servicesWithIllegalAnnotations));
		});
		catchThrowable(() -> {
			ServiceRules.services_must_not_also_be_annotated_with_repository.check(TestUtils.computeClasses(servicesWithIllegalAnnotations));
		});
		catchThrowable(() -> {
			ServiceRules.services_must_not_also_be_annotated_with_value_object.check(TestUtils.computeClasses(servicesWithIllegalAnnotations));
		});

		catchThrowable(() -> {
			ServiceRules.services_must_implement_an_interface_in_the_same_layer.check(TestUtils.computeClasses(servicesWithInterfaceOutsideSameLayer));
		});

		catchThrowable(() -> {
			ServiceRules.services_fields_must_be_final.check(TestUtils.computeClasses(servicesWithoutFinalField));
		});
	}
}