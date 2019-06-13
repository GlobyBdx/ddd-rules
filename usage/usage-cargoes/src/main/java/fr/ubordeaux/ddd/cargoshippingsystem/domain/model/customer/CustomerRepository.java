package fr.ubordeaux.ddd.cargoshippingsystem.domain.model.customer;

import java.util.Set;

import fr.ubordeaux.ddd.cargoshippingsystem.domain.model.billing.InvoiceID;
import fr.ubordeaux.ddd.cargoshippingsystem.domain.model.shipping.CargoTrackingID;

/**
 * Repository interface
 * 
 */

public interface CustomerRepository {
	public void store(Customer customer);
	public Set<Customer> findAll();
	public Customer findByCustomerID(CustomerID customerID);
	public Customer findByCustomerName(CustomerName customerName);
	public Set<Customer> findByCustomerBook(CustomerBook customerBook);
	public Set<Customer> findByCargoTrackingID(CargoTrackingID cargoTrackingID);
	public Set<Customer> findByInvoiceID(InvoiceID invoiceID);
}