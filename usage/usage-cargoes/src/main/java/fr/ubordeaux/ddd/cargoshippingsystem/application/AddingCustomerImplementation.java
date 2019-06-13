package fr.ubordeaux.ddd.cargoshippingsystem.application;

import fr.ubordeaux.ddd.cargoshippingsystem.domain.model.customer.Customer;
import fr.ubordeaux.ddd.cargoshippingsystem.domain.model.customer.CustomerID;
import fr.ubordeaux.ddd.cargoshippingsystem.domain.model.customer.CustomerName;
import fr.ubordeaux.ddd.cargoshippingsystem.domain.model.customer.CustomerRepository;
import fr.ubordeaux.ddd.annotations.types.Service;

/**
 * Service implementation
 * 
 */

@Service
public class AddingCustomerImplementation implements AddingCustomer {
	private final CustomerRepository customerRepository;

	public AddingCustomerImplementation(CustomerRepository customerRepository) {
		this.customerRepository = customerRepository;
	}

	@Override
	public void addCustomer(CustomerID customerID) {
		this.customerRepository.store(new Customer(customerID));
	}

	@Override
	public void addCustomer(CustomerID customerID, CustomerName customerName) {
		Customer customer = new Customer(customerID);
		customer.setCustomerName(customerName);
		this.customerRepository.store(customer);
	}
}