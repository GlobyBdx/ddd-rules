package fr.ubordeaux.ddd.tests;

import com.tngtech.archunit.core.domain.JavaClasses;

import fr.ubordeaux.ddd.rules.RepositoryRules;
import fr.ubordeaux.ddd.rules.utils.PackageUtils;
import fr.ubordeaux.ddd.tests.utils.ArchUtils;

public class RepositoryTest implements ArchRuleTest {
	@Override
	public void execute(String path) {
		JavaClasses classes = ArchUtils.importAllClassesInPackage(path, SRC_CLASSES_FOLDER);

		PackageUtils.importAllPackageInfoClasses(classes);

		RepositoryRules.repositories_must_reside_in_a_package_annotated_with_infrastructure.check(classes);

		RepositoryRules.repositories_must_not_also_be_annotated_with_entity.check(classes);
		RepositoryRules.repositories_must_not_also_be_annotated_with_factory.check(classes);
		RepositoryRules.repositories_must_not_also_be_annotated_with_service.check(classes);
		RepositoryRules.repositories_must_not_also_be_annotated_with_value_object.check(classes);

		RepositoryRules.repositories_must_implement_an_interface_in_a_domain_layer.check(classes);

		RepositoryRules.repositories_must_access_at_least_one_entity_or_value_object.check(classes);
	}
}