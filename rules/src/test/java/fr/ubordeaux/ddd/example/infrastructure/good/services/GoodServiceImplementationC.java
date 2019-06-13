package fr.ubordeaux.ddd.example.infrastructure.good.services;

import java.util.Set;

import fr.ubordeaux.ddd.annotations.types.Service;

/**
 * Service implementation
 * 
 */

@Service
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