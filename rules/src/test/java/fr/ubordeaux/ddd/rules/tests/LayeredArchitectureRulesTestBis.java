package fr.ubordeaux.ddd.rules.tests;

import com.google.common.collect.Sets;
import com.tngtech.archunit.core.domain.JavaClasses;
import com.tngtech.archunit.core.importer.ClassFileImporter;
import com.tngtech.archunit.lang.ArchRule;

import fr.ubordeaux.ddd.example.anticorruption.application.domain.infrastructure.presentation.evil.EvilMultipleLayersToken;
import fr.ubordeaux.ddd.example.anticorruption.evil.EvilTokenAccessingPresentationLayerA;
import fr.ubordeaux.ddd.example.anticorruption.neutral.NeutralAnticorruptionLayerToken;
import fr.ubordeaux.ddd.example.anticorruptionbis.neutral.NeutralAnticorruptionLayerTokenBis;
import fr.ubordeaux.ddd.example.application.neutral.NeutralApplicationLayerToken;
import fr.ubordeaux.ddd.example.domain.model.evil.EvilTokenAccessingAnticorruptionLayer;
import fr.ubordeaux.ddd.example.domain.model.evil.EvilTokenAccessingAnticorruptionLayerBis;
import fr.ubordeaux.ddd.example.domain.model.evil.EvilTokenAccessingApplicationLayer;
import fr.ubordeaux.ddd.example.domain.model.evil.EvilTokenAccessingGoodEntityAConstructor;
import fr.ubordeaux.ddd.example.domain.model.evil.EvilTokenAccessingGoodValueObjectAConstructor;
import fr.ubordeaux.ddd.example.domain.model.evil.EvilTokenAccessingInfrastructureLayer;
import fr.ubordeaux.ddd.example.domain.model.evil.EvilTokenAccessingMultipleLayers;
import fr.ubordeaux.ddd.example.domain.model.evil.EvilTokenAccessingPresentationLayerB;
import fr.ubordeaux.ddd.example.domain.model.evil.EvilTokenWithMultipleAnnotations;
import fr.ubordeaux.ddd.example.domain.neutral.NeutralDomainLayerToken;
import fr.ubordeaux.ddd.example.evil.services.EvilServiceImplementationOutsideAnyLayer;
import fr.ubordeaux.ddd.example.infrastructure.neutral.NeutralInfrastructureLayerToken;
import fr.ubordeaux.ddd.example.presentation.neutral.NeutralPresentationLayerToken;
import fr.ubordeaux.ddd.rules.LayeredArchitectureRules;
import fr.ubordeaux.ddd.rules.tests.utils.TestUtils;
import fr.ubordeaux.ddd.rules.utils.PackageUtils;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.assertj.core.api.Assertions.*;

import java.util.Set;

public class LayeredArchitectureRulesTestBis {
	final private static JavaClasses classes = new ClassFileImporter().importPackages("fr.ubordeaux.ddd.example");

	final private Set<Class<?>> tokens =
			Sets.newHashSet(
					EvilMultipleLayersToken.class,
					EvilTokenAccessingPresentationLayerA.class,
					NeutralAnticorruptionLayerToken.class,
					NeutralAnticorruptionLayerTokenBis.class,
					NeutralApplicationLayerToken.class,
					EvilTokenAccessingAnticorruptionLayer.class,
					EvilTokenAccessingAnticorruptionLayerBis.class,
					EvilTokenAccessingApplicationLayer.class,
					EvilTokenAccessingGoodEntityAConstructor.class,
					EvilTokenAccessingGoodValueObjectAConstructor.class,
					EvilTokenAccessingInfrastructureLayer.class,
					EvilTokenAccessingMultipleLayers.class,
					EvilTokenAccessingPresentationLayerB.class,
					EvilTokenWithMultipleAnnotations.class,
					NeutralDomainLayerToken.class,
					EvilServiceImplementationOutsideAnyLayer.class,
					NeutralInfrastructureLayerToken.class,
					NeutralPresentationLayerToken.class);

	final private Set<Class<?>> layersInsideOtherLayers =
			Sets.newHashSet(
					EvilMultipleLayersToken.class);

	final private Set<Class<?>> namedLayersWithIllegalAnticorruptionLayerAccesses =
			Sets.newHashSet(
					EvilTokenAccessingAnticorruptionLayer.class,
					EvilTokenAccessingMultipleLayers.class);

	final private Set<Class<?>> annotatedLayersWithIllegalAnticorruptionLayerAccesses =
			Sets.newHashSet(
					EvilTokenAccessingAnticorruptionLayer.class,
					EvilTokenAccessingAnticorruptionLayerBis.class,
					EvilTokenAccessingMultipleLayers.class);

	final private Set<Class<?>> layersWithIllegalApplicationLayerAccesses =
			Sets.newHashSet(
					EvilTokenAccessingApplicationLayer.class,
					EvilTokenAccessingMultipleLayers.class,
					EvilServiceImplementationOutsideAnyLayer.class);

	final private Set<Class<?>> layersWithIllegalInfrastructureLayerAccesses =
			Sets.newHashSet(
					EvilTokenAccessingInfrastructureLayer.class,
					EvilTokenAccessingMultipleLayers.class);

	final private Set<Class<?>> layersWithIllegalPresentationLayerAccesses =
			Sets.newHashSet(
					EvilTokenAccessingPresentationLayerA.class,
					EvilTokenAccessingPresentationLayerB.class,
					EvilTokenAccessingMultipleLayers.class);

	@BeforeClass
	public static void beforeAllTestMethods() {
		PackageUtils.importAllPackageInfoClasses(classes);
	}

	@AfterClass
	public static void afterAllTestMethods() {
		PackageUtils.clearAllPackageInfoClasses();
	}

	private void assertThatCodeExcept(ArchRule rule, Set<Class<?>> exceptions) {
		assertThatCode(() -> rule.check(TestUtils.computeClassesExcept(tokens, exceptions))).doesNotThrowAnyException();
	}

	@Test
	public void shouldNotThrowAnyViolation() {
		assertThatCodeExcept(LayeredArchitectureRules.named_anticorruption_layer_dependencies_must_be_respected, namedLayersWithIllegalAnticorruptionLayerAccesses);
		assertThatCodeExcept(LayeredArchitectureRules.named_anticorruption_layer_classes_must_not_reside_in_another_layer, layersInsideOtherLayers);

		assertThatCodeExcept(LayeredArchitectureRules.named_application_layer_dependencies_must_be_respected, layersWithIllegalApplicationLayerAccesses);
		assertThatCodeExcept(LayeredArchitectureRules.named_application_layer_classes_must_not_reside_in_another_layer, layersInsideOtherLayers);

		assertThatCodeExcept(LayeredArchitectureRules.named_domain_layer_classes_must_not_reside_in_another_layer, layersInsideOtherLayers);

		assertThatCodeExcept(LayeredArchitectureRules.named_infrastructure_layer_dependencies_must_be_respected, layersWithIllegalInfrastructureLayerAccesses);
		assertThatCodeExcept(LayeredArchitectureRules.named_infrastructure_layer_classes_must_not_reside_in_another_layer, layersInsideOtherLayers);

		assertThatCodeExcept(LayeredArchitectureRules.named_presentation_layer_dependencies_must_be_respected, layersWithIllegalPresentationLayerAccesses);
		assertThatCodeExcept(LayeredArchitectureRules.named_presentation_layer_classes_must_not_reside_in_another_layer, layersInsideOtherLayers);

		assertThatCodeExcept(LayeredArchitectureRules.annotated_anticorruption_layer_dependencies_must_be_respected, annotatedLayersWithIllegalAnticorruptionLayerAccesses);
		assertThatCodeExcept(LayeredArchitectureRules.annotated_anticorruption_layer_classes_must_not_reside_in_another_layer, layersInsideOtherLayers);

		assertThatCodeExcept(LayeredArchitectureRules.annotated_application_layer_dependencies_must_be_respected, layersWithIllegalApplicationLayerAccesses);
		assertThatCodeExcept(LayeredArchitectureRules.annotated_application_layer_classes_must_not_reside_in_another_layer, layersInsideOtherLayers);

		assertThatCodeExcept(LayeredArchitectureRules.annotated_domain_layer_classes_must_not_reside_in_another_layer, layersInsideOtherLayers);

		assertThatCodeExcept(LayeredArchitectureRules.annotated_infrastructure_layer_dependencies_must_be_respected, layersWithIllegalInfrastructureLayerAccesses);
		assertThatCodeExcept(LayeredArchitectureRules.annotated_infrastructure_layer_classes_must_not_reside_in_another_layer, layersInsideOtherLayers);

		assertThatCodeExcept(LayeredArchitectureRules.annotated_presentation_layer_dependencies_must_be_respected, layersWithIllegalPresentationLayerAccesses);
		assertThatCodeExcept(LayeredArchitectureRules.annotated_presentation_layer_classes_must_not_reside_in_another_layer, layersInsideOtherLayers);
	}

	@Test
	public void shouldThrowViolations() {
		catchThrowable(() -> {
			LayeredArchitectureRules.named_anticorruption_layer_dependencies_must_be_respected.check(TestUtils.computeClasses(namedLayersWithIllegalAnticorruptionLayerAccesses));
		});
		catchThrowable(() -> {
			LayeredArchitectureRules.named_anticorruption_layer_classes_must_not_reside_in_another_layer.check(TestUtils.computeClasses(layersInsideOtherLayers));
		});

		catchThrowable(() -> {
			LayeredArchitectureRules.named_application_layer_dependencies_must_be_respected.check(TestUtils.computeClasses(layersWithIllegalApplicationLayerAccesses));
		});
		catchThrowable(() -> {
			LayeredArchitectureRules.named_application_layer_classes_must_not_reside_in_another_layer.check(TestUtils.computeClasses(layersInsideOtherLayers));
		});

		catchThrowable(() -> {
			LayeredArchitectureRules.named_domain_layer_classes_must_not_reside_in_another_layer.check(TestUtils.computeClasses(layersInsideOtherLayers));
		});

		catchThrowable(() -> {
			LayeredArchitectureRules.named_infrastructure_layer_dependencies_must_be_respected.check(TestUtils.computeClasses(layersWithIllegalInfrastructureLayerAccesses));
		});
		catchThrowable(() -> {
			LayeredArchitectureRules.named_infrastructure_layer_classes_must_not_reside_in_another_layer.check(TestUtils.computeClasses(layersInsideOtherLayers));
		});

		catchThrowable(() -> {
			LayeredArchitectureRules.named_presentation_layer_dependencies_must_be_respected.check(TestUtils.computeClasses(layersWithIllegalPresentationLayerAccesses));
		});
		catchThrowable(() -> {
			LayeredArchitectureRules.named_presentation_layer_classes_must_not_reside_in_another_layer.check(TestUtils.computeClasses(layersInsideOtherLayers));
		});

		catchThrowable(() -> {
			LayeredArchitectureRules.annotated_anticorruption_layer_dependencies_must_be_respected.check(TestUtils.computeClasses(annotatedLayersWithIllegalAnticorruptionLayerAccesses));
		});
		catchThrowable(() -> {
			LayeredArchitectureRules.annotated_anticorruption_layer_classes_must_not_reside_in_another_layer.check(TestUtils.computeClasses(layersInsideOtherLayers));
		});

		catchThrowable(() -> {
			LayeredArchitectureRules.annotated_application_layer_dependencies_must_be_respected.check(TestUtils.computeClasses(layersWithIllegalApplicationLayerAccesses));
		});
		catchThrowable(() -> {
			LayeredArchitectureRules.annotated_application_layer_classes_must_not_reside_in_another_layer.check(TestUtils.computeClasses(layersInsideOtherLayers));
		});

		catchThrowable(() -> {
			LayeredArchitectureRules.annotated_domain_layer_classes_must_not_reside_in_another_layer.check(TestUtils.computeClasses(layersInsideOtherLayers));
		});

		catchThrowable(() -> {
			LayeredArchitectureRules.annotated_infrastructure_layer_dependencies_must_be_respected.check(TestUtils.computeClasses(layersWithIllegalInfrastructureLayerAccesses));
		});
		catchThrowable(() -> {
			LayeredArchitectureRules.annotated_infrastructure_layer_classes_must_not_reside_in_another_layer.check(TestUtils.computeClasses(layersInsideOtherLayers));
		});

		catchThrowable(() -> {
			LayeredArchitectureRules.annotated_presentation_layer_dependencies_must_be_respected.check(TestUtils.computeClasses(layersWithIllegalPresentationLayerAccesses));
		});
		catchThrowable(() -> {
			LayeredArchitectureRules.annotated_presentation_layer_classes_must_not_reside_in_another_layer.check(TestUtils.computeClasses(layersInsideOtherLayers));
		});
	}
}