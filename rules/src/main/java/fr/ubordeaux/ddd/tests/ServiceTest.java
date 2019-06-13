package fr.ubordeaux.ddd.tests;

import com.tngtech.archunit.core.domain.JavaClasses;

import fr.ubordeaux.ddd.rules.ServiceRules;
import fr.ubordeaux.ddd.rules.utils.PackageUtils;
import fr.ubordeaux.ddd.tests.utils.ArchUtils;

public class ServiceTest implements ArchRuleTest {
	@Override
	public void execute(String path) {
		JavaClasses classes = ArchUtils.importAllClassesInPackage(path, SRC_CLASSES_FOLDER);

		PackageUtils.importAllPackageInfoClasses(classes);

		ServiceRules.services_must_reside_in_a_package_annotated_with_application_or_domain_or_infrastructure.check(classes);

		ServiceRules.services_must_not_also_be_annotated_with_entity.check(classes);
		ServiceRules.services_must_not_also_be_annotated_with_factory.check(classes);
		ServiceRules.services_must_not_also_be_annotated_with_repository.check(classes);
		ServiceRules.services_must_not_also_be_annotated_with_value_object.check(classes);

		ServiceRules.services_must_implement_an_interface_in_the_same_layer.check(classes);

		ServiceRules.services_fields_must_be_final.check(classes);
	}
}