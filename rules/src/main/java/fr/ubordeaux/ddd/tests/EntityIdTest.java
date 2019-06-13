package fr.ubordeaux.ddd.tests;

import com.tngtech.archunit.core.domain.JavaClasses;

import fr.ubordeaux.ddd.rules.EntityIdRules;
import fr.ubordeaux.ddd.tests.utils.ArchUtils;

public class EntityIdTest implements ArchRuleTest {
	@Override
	public void execute(String path) {
		JavaClasses classes = ArchUtils.importAllClassesInPackage(path, SRC_CLASSES_FOLDER);

		EntityIdRules.entity_ids_must_only_be_used_by_entities.check(classes);

		EntityIdRules.entity_ids_must_be_private.check(classes);

		EntityIdRules.entity_ids_must_have_private_annotated_setters.check(classes);
		EntityIdRules.entity_ids_must_have_specific_annotated_getters.check(classes);
		EntityIdRules.entity_ids_must_have_private_accesses.check(classes);
	}
}