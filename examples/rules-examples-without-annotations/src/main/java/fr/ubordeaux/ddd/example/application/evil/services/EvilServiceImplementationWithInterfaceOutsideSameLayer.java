package fr.ubordeaux.ddd.example.application.evil.services;

import fr.ubordeaux.ddd.example.domain.model.good.aggregates.GoodAggregate;
import fr.ubordeaux.ddd.example.domain.model.good.factories.GoodFactory;
import fr.ubordeaux.ddd.example.domain.model.good.repositories.GoodRepository;
import fr.ubordeaux.ddd.example.evil.services.EvilServiceOutsideAnyLayer;

/**
 * Service implementation
 * 
 */

public class EvilServiceImplementationWithInterfaceOutsideSameLayer implements EvilServiceOutsideAnyLayer {
	final private GoodRepository repository;
	final private GoodFactory factory;

	public EvilServiceImplementationWithInterfaceOutsideSameLayer(GoodRepository repository, GoodFactory factory) {
		this.repository = repository;
		this.factory = factory;
	}

	@Override
	public void addAggregate(String valueId) {
		this.repository.store(factory.newAggregate(valueId));
	}

	@Override
	public void addAggregate(String valueId, String value) {
		this.repository.store(factory.newAggregate(valueId, value));
	}

	@Override
	public void addAggregate(String valueId, GoodAggregate prototype) {
		this.repository.store(factory.newAggregate(valueId, prototype));
	}

	@Override
	public void addAggregate(GoodAggregate aggregate) {
		this.repository.store(aggregate);
	}
}