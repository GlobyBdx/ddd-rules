package fr.ubordeaux.ddd.cargoshippingsystem.userinterface;

import fr.ubordeaux.ddd.cargoshippingsystem.application.AddingCustomer;
import fr.ubordeaux.ddd.cargoshippingsystem.domain.model.customer.CustomerID;
import fr.ubordeaux.ddd.cargoshippingsystem.domain.model.customer.CustomerName;

/**
 * Interface implementation
 * 
 */

public class AddingCustomerInterfaceImplementation implements AddingCustomerInterface {
	private AddingCustomer addingCustomer;

	public void setAddingCustomer(AddingCustomer addingCustomer) {
		this.addingCustomer = addingCustomer;
	}

	@Override
	public void addCustomer(String customerID) {
		this.addingCustomer.addCustomer(new CustomerID(customerID));
	}

	@Override
	public void addCustomer(String customerID, String customerName) {
		this.addingCustomer.addCustomer(new CustomerID(customerID), new CustomerName(customerName));
	}
}