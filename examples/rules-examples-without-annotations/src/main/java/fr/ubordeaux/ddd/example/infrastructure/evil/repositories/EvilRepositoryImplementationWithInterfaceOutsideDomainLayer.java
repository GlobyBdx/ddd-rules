package fr.ubordeaux.ddd.example.infrastructure.evil.repositories;

import java.util.HashSet;
import java.util.Set;

import fr.ubordeaux.ddd.example.domain.model.good.aggregates.GoodAggregate;
import fr.ubordeaux.ddd.example.domain.model.good.entities.GoodEntityB;
import fr.ubordeaux.ddd.example.domain.model.good.entities.GoodEntityC;
import fr.ubordeaux.ddd.example.evil.repositories.EvilRepositoryOutsideDomainLayer;

/**
 * Repository implementation
 * 
 */

public class EvilRepositoryImplementationWithInterfaceOutsideDomainLayer implements EvilRepositoryOutsideDomainLayer {
	private Set<GoodAggregate> aggregates;

	public EvilRepositoryImplementationWithInterfaceOutsideDomainLayer() {
		this.aggregates = new HashSet<>();
	}

	@Override
	public void store(GoodAggregate aggregate) {
		if (this.findAll().contains(aggregate)) {
			this.aggregates.remove(aggregate);
		}
		this.aggregates.add(aggregate);
	}

	@Override
	public Set<GoodAggregate> findAll() {
		return aggregates;
	}

	@Override
	public GoodAggregate findByEntityId(GoodEntityB entityId) {
		for (GoodAggregate aggregate : this.findAll()) {
			if (aggregate.getEntityID().equals(entityId)) {
				return aggregate;
			}
		}
		return null;
	}

	@Override
	public Set<GoodAggregate> findByEntity(GoodEntityC entity) {
		Set<GoodAggregate> aggregates = new HashSet<>();
		for (GoodAggregate aggregate : this.findAll()) {
			if (aggregate.getEntity().equals(entity)) {
				aggregates.add(aggregate);
			}
		}
		return aggregates;
	}

	@Override
	public Set<GoodAggregate> findByValue(String value) {
		Set<GoodAggregate> aggregates = new HashSet<>();
		for (GoodAggregate aggregate : this.findAll()) {
			if (aggregate.getValue().compareTo(value) == 0) {
				aggregates.add(aggregate);
			}
		}
		return aggregates;
	}
}