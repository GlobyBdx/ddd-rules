package fr.ubordeaux.ddd.tests;

import com.tngtech.archunit.core.domain.JavaClasses;

import fr.ubordeaux.ddd.rules.EntityRules;
import fr.ubordeaux.ddd.rules.utils.PackageUtils;
import fr.ubordeaux.ddd.tests.utils.ArchUtils;

public class EntityTest implements ArchRuleTest {
	@Override
	public void execute(String path) {
		JavaClasses classes = ArchUtils.importAllClassesInPackage(path, SRC_CLASSES_FOLDER);

		PackageUtils.importAllPackageInfoClasses(classes);

		EntityRules.entities_must_reside_in_a_package_annotated_with_domain.check(classes);

		EntityRules.entities_must_not_also_be_annotated_with_factory.check(classes);
		EntityRules.entities_must_not_also_be_annotated_with_repository.check(classes);
		EntityRules.entities_must_not_also_be_annotated_with_service.check(classes);
		EntityRules.entities_must_not_also_be_annotated_with_value_object.check(classes);

		EntityRules.entities_must_have_at_least_one_entity_id.check(classes);

		EntityRules.entities_must_override_object_class_equals_method_accessing_all_entity_ids.check(classes);
		EntityRules.entities_must_override_object_class_hash_code_method_accessing_all_entity_ids.check(classes);
		EntityRules.entities_must_override_object_class_to_string_method_accessing_all_entity_ids.check(classes);
	}
}