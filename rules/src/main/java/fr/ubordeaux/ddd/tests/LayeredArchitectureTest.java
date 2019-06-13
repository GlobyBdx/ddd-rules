package fr.ubordeaux.ddd.tests;

import com.tngtech.archunit.core.domain.JavaClasses;

import fr.ubordeaux.ddd.rules.LayeredArchitectureRules;
import fr.ubordeaux.ddd.rules.utils.PackageUtils;
import fr.ubordeaux.ddd.tests.utils.ArchUtils;

public class LayeredArchitectureTest implements ArchRuleTest {
	@Override
	public void execute(String path) {
		JavaClasses classes = ArchUtils.importAllClassesInPackage(path, SRC_CLASSES_FOLDER);

		PackageUtils.importAllPackageInfoClasses(classes);

		LayeredArchitectureRules.annotated_anticorruption_layer_dependencies_must_be_respected.check(classes);
		LayeredArchitectureRules.annotated_anticorruption_layer_classes_must_not_reside_in_another_layer.check(classes);

		LayeredArchitectureRules.annotated_application_layer_dependencies_must_be_respected.check(classes);
		LayeredArchitectureRules.annotated_application_layer_classes_must_not_reside_in_another_layer.check(classes);

		LayeredArchitectureRules.annotated_domain_layer_classes_must_not_reside_in_another_layer.check(classes);

		LayeredArchitectureRules.annotated_infrastructure_layer_dependencies_must_be_respected.check(classes);
		LayeredArchitectureRules.annotated_infrastructure_layer_classes_must_not_reside_in_another_layer.check(classes);

		LayeredArchitectureRules.annotated_presentation_layer_dependencies_must_be_respected.check(classes);
		LayeredArchitectureRules.annotated_presentation_layer_classes_must_not_reside_in_another_layer.check(classes);
	}
}