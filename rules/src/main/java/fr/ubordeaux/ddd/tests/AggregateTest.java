package fr.ubordeaux.ddd.tests;

import com.tngtech.archunit.core.domain.JavaClasses;

import fr.ubordeaux.ddd.rules.AggregateRules;
import fr.ubordeaux.ddd.tests.utils.ArchUtils;

public class AggregateTest implements ArchRuleTest {
	@Override
	public void execute(String path) {
		JavaClasses classes = ArchUtils.importAllClassesInPackage(path, SRC_CLASSES_FOLDER);

		AggregateRules.aggregates_must_also_be_annotated_with_entity.check(classes);

		AggregateRules.aggregates_internal_objects_must_only_be_externally_instantiated_by_owner_aggregates_specific_factories.check(classes);
	}
}