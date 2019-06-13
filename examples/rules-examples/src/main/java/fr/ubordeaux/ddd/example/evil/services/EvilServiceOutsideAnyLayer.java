package fr.ubordeaux.ddd.example.evil.services;

import fr.ubordeaux.ddd.example.domain.model.good.aggregates.GoodAggregate;

/**
 * Service interface
 * 
 */

public interface EvilServiceOutsideAnyLayer {
	public void addAggregate(String valueId);
	public void addAggregate(String valueId, String value);
	public void addAggregate(String valueId, GoodAggregate prototype);
	public void addAggregate(GoodAggregate aggregate);
}