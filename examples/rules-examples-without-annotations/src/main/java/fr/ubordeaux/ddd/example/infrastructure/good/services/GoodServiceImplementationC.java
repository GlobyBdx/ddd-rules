package fr.ubordeaux.ddd.example.infrastructure.good.services;

import java.util.Set;

/**
 * Service implementation
 * 
 */

public class GoodServiceImplementationC implements GoodServiceC {
	@Override
	public void connectDatabaseClient(String host, String port) {}

	@Override
	public void storeElement(String value) {}

	@Override
	public String findElementByValueId(String valueId) {
		return null;
	}

	@Override
	public Set<String> findElementByValue(String value) {
		return null;
	}
}