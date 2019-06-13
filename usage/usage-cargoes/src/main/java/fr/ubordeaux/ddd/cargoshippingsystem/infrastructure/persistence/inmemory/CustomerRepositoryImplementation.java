package fr.ubordeaux.ddd.cargoshippingsystem.infrastructure.persistence.inmemory;

import java.util.HashSet;
import java.util.Set;

import fr.ubordeaux.ddd.cargoshippingsystem.domain.model.billing.InvoiceID;
import fr.ubordeaux.ddd.cargoshippingsystem.domain.model.customer.Customer;
import fr.ubordeaux.ddd.cargoshippingsystem.domain.model.customer.CustomerBook;
import fr.ubordeaux.ddd.cargoshippingsystem.domain.model.customer.CustomerID;
import fr.ubordeaux.ddd.cargoshippingsystem.domain.model.customer.CustomerName;
import fr.ubordeaux.ddd.cargoshippingsystem.domain.model.customer.CustomerRepository;
import fr.ubordeaux.ddd.cargoshippingsystem.domain.model.shipping.CargoTrackingID;
import fr.ubordeaux.ddd.annotations.types.Repository;

/**
 * Repository implementation
 * 
 */

@Repository
public class CustomerRepositoryImplementation implements CustomerRepository {
	private Set<Customer> customers;

	public CustomerRepositoryImplementation() {
		this.customers = new HashSet<>();
	}

	@Override
	public void store(Customer customer) {
		if (this.findAll().contains(customer)) {
			this.customers.remove(customer);
		}
		this.customers.add(customer);
	}

	@Override
	public Set<Customer> findAll() {
		return this.customers;
	}

	@Override
	public Customer findByCustomerID(CustomerID customerID) {
		for (Customer customer : this.findAll()) {
			if (customer.getCustomerID().equals(customerID)) {
				return customer;
			}
		}
		return null;
	}

	@Override
	public Customer findByCustomerName(CustomerName customerName) {
		for (Customer customer : this.findAll()) {
			if (customer.getCustomerName().equals(customerName)) {
				return customer;
			}
		}
		return null;
	}

	@Override
	public Set<Customer> findByCustomerBook(CustomerBook customerBook) {
		Set<Customer> customers = new HashSet<>();
		for (Customer customer : this.findAll()) {
			for (CustomerBook book : customer.getCustomerBooks()) {
				if (book.equals(customerBook)) {
					customers.add(customer);
				}
			}
		}
		return customers;
	}

	@Override
	public Set<Customer> findByCargoTrackingID(CargoTrackingID cargoTrackingID) {
		Set<Customer> customers = new HashSet<>();
		for (Customer customer : this.findAll()) {
			for (CustomerBook book : customer.getCustomerBooks()) {
				if (book.getCargoTrackingID().equals(cargoTrackingID)) {
					customers.add(customer);
				}
			}
		}
		return customers;
	}

	@Override
	public Set<Customer> findByInvoiceID(InvoiceID invoiceID) {
		Set<Customer> customers = new HashSet<>();
		for (Customer customer : this.findAll()) {
			for (CustomerBook book : customer.getCustomerBooks()) {
				if (book.getInvoiceID().equals(invoiceID)) {
					customers.add(customer);
				}
			}
		}
		return customers;
	}
}