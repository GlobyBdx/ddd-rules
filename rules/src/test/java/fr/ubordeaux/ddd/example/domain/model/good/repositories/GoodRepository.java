package fr.ubordeaux.ddd.example.domain.model.good.repositories;

import java.util.Set;

import fr.ubordeaux.ddd.example.domain.model.good.aggregates.GoodAggregate;
import fr.ubordeaux.ddd.example.domain.model.good.entities.GoodEntityB;
import fr.ubordeaux.ddd.example.domain.model.good.entities.GoodEntityC;

/**
 * Repository interface
 * 
 */

public interface GoodRepository {
	public void store(GoodAggregate aggregate);
	public Set<GoodAggregate> findAll();
	public GoodAggregate findByEntityId(GoodEntityB entityId);
	public Set<GoodAggregate> findByEntity(GoodEntityC entity);
	public Set<GoodAggregate> findByValue(String value);
}