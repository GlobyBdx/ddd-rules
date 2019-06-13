package fr.ubordeaux.ddd.example.infrastructure.good.services;

import java.util.Set;

/**
 * Service interface
 * 
 */

public interface GoodServiceC {
	public void connectDatabaseClient(String host, String port);
	public void storeElement(String value);
	public String findElementByValueId(String valueId);
	public Set<String> findElementByValue(String value);
}