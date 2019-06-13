package fr.ubordeaux.ddd.cargoshippingsystem.application;

import fr.ubordeaux.ddd.cargoshippingsystem.domain.model.customer.CustomerID;
import fr.ubordeaux.ddd.cargoshippingsystem.domain.model.customer.CustomerName;

/**
 * Service interface
 * 
 */

public interface AddingCustomer {
	public void addCustomer(CustomerID customerID);
	public void addCustomer(CustomerID customerID, CustomerName customerName);
}