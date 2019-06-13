package fr.ubordeaux.ddd.example.application.good.services;

import fr.ubordeaux.ddd.example.domain.model.good.aggregates.GoodAggregate;

/**
 * Service interface
 * 
 */

public interface GoodServiceA {
	public void addAggregate(String valueId);
	public void addAggregate(String valueId, String value);
	public void addAggregate(String valueId, GoodAggregate prototype);
	public void addAggregate(GoodAggregate aggregate);
}