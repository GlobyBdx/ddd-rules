package fr.ubordeaux.ddd.tests;

import com.tngtech.archunit.core.domain.JavaClasses;

import fr.ubordeaux.ddd.rules.ValueObjectRules;
import fr.ubordeaux.ddd.rules.utils.PackageUtils;
import fr.ubordeaux.ddd.tests.utils.ArchUtils;

public class ValueObjectTest implements ArchRuleTest {
	@Override
	public void execute(String path) {
		JavaClasses classes = ArchUtils.importAllClassesInPackage(path, SRC_CLASSES_FOLDER);

		PackageUtils.importAllPackageInfoClasses(classes);

		ValueObjectRules.value_objects_must_reside_in_a_package_annotated_with_domain.check(classes);

		ValueObjectRules.value_objects_must_not_also_be_annotated_with_entity.check(classes);
		ValueObjectRules.value_objects_must_not_also_be_annotated_with_factory.check(classes);
		ValueObjectRules.value_objects_must_not_also_be_annotated_with_repository.check(classes);
		ValueObjectRules.value_objects_must_not_also_be_annotated_with_service.check(classes);

		ValueObjectRules.value_objects_must_have_at_least_one_field.check(classes);

		ValueObjectRules.value_objects_must_override_object_class_equals_method_accessing_all_fields.check(classes);
		ValueObjectRules.value_objects_must_override_object_class_hash_code_method_accessing_all_fields.check(classes);
		ValueObjectRules.value_objects_must_override_object_class_to_string_method_accessing_all_fields.check(classes);

		ValueObjectRules.value_objects_fields_must_be_private.check(classes);

		ValueObjectRules.value_objects_fields_must_have_private_annotated_setters.check(classes);
		ValueObjectRules.value_objects_fields_must_have_specific_annotated_getters.check(classes);
		ValueObjectRules.value_objects_fields_must_have_private_accesses.check(classes);
	}
}