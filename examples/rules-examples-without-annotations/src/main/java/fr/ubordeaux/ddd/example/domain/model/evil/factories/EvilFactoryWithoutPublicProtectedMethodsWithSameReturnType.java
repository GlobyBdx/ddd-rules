package fr.ubordeaux.ddd.example.domain.model.evil.factories;

import fr.ubordeaux.ddd.example.domain.model.good.aggregates.GoodAggregate;
import fr.ubordeaux.ddd.example.domain.model.good.entities.GoodEntityB;
import fr.ubordeaux.ddd.example.domain.model.good.entities.GoodEntityC;
import fr.ubordeaux.ddd.example.domain.model.good.valueobjects.GoodValueObjectD;
import fr.ubordeaux.ddd.example.domain.model.good.valueobjects.GoodValueObjectE;

/**
 * Factory
 * 
 */

public class EvilFactoryWithoutPublicProtectedMethodsWithSameReturnType {
	public GoodAggregate newAggregate(String valueId) {
		return new GoodAggregate(new GoodEntityB(new GoodValueObjectD(valueId)));
	}

	public String newAggregate(String valueId, String value) {
		GoodAggregate aggregate = newAggregate(valueId);
		aggregate.setEntity(new GoodEntityC(new GoodValueObjectE(valueId)));
		return aggregate.toString();
	}

	public void newAggregate(String valueId, GoodAggregate prototype) {
		GoodAggregate aggregate = newAggregate(valueId);
		aggregate.setEntity(prototype.getEntity());
		aggregate.setValue(prototype.getValue());
		return;
	}
}