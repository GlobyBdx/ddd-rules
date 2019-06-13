package fr.ubordeaux.ddd.tests;

import com.tngtech.archunit.core.domain.JavaClasses;

import fr.ubordeaux.ddd.rules.FactoryRules;
import fr.ubordeaux.ddd.rules.utils.PackageUtils;
import fr.ubordeaux.ddd.tests.utils.ArchUtils;

public class FactoryTest implements ArchRuleTest {
	@Override
	public void execute(String path) {
		JavaClasses classes = ArchUtils.importAllClassesInPackage(path, SRC_CLASSES_FOLDER);

		PackageUtils.importAllPackageInfoClasses(classes);

		FactoryRules.factories_must_reside_in_a_package_annotated_with_domain.check(classes);

		FactoryRules.factories_must_not_also_be_annotated_with_entity.check(classes);
		FactoryRules.factories_must_not_also_be_annotated_with_repository.check(classes);
		FactoryRules.factories_must_not_also_be_annotated_with_service.check(classes);
		FactoryRules.factories_must_not_also_be_annotated_with_value_object.check(classes);

		FactoryRules.factories_must_access_at_least_one_constructor_from_an_entity_or_a_value_object.check(classes);

		FactoryRules.factories_must_have_public_and_protected_methods_with_the_same_return_type.check(classes);
	}
}