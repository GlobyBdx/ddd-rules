package fr.ubordeaux.ddd.example.domain.model.evil.factories;

import fr.ubordeaux.ddd.annotations.types.Factory;
import fr.ubordeaux.ddd.example.domain.model.good.aggregates.GoodAggregate;

/**
 * Factory
 * 
 */

@Factory
public class EvilFactoryWithoutEntityValueObjectConstructorAccess {
	public GoodAggregate newAggregate(String valueId) {
		return null;
	}

	public GoodAggregate newAggregate(String valueId, String value) {
		return null;
	}

	public GoodAggregate newAggregate(String valueId, GoodAggregate prototype) {
		return null;
	}
}