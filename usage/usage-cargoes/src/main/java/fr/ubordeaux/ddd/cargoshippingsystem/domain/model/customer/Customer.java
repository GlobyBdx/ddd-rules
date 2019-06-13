package fr.ubordeaux.ddd.cargoshippingsystem.domain.model.customer;

import java.util.HashSet;
import java.util.Set;

import fr.ubordeaux.ddd.annotations.types.Entity;
import fr.ubordeaux.ddd.annotations.fields.EntityID;

/**
 * Entity
 * 
 */

@Entity
public class Customer {
	@EntityID
	private CustomerID customerID;
	private CustomerName customerName;
	private Set<CustomerBook> customerBooks;

	public Customer(CustomerID customerID) {
		this.setCustomerID(customerID);
		this.customerBooks = new HashSet<>();
	}

	private void setCustomerID(CustomerID customerID) {
		if (customerID == null) throw new CustomerException("Cannot create Customer with a null CustomerID.");
		this.customerID = customerID;
	}

	public void setCustomerName(CustomerName customerName) {
		this.customerName = customerName;
	}

	public CustomerID getCustomerID() {
		return this.customerID;
	}

	public CustomerName getCustomerName() {
		return this.customerName;
	}

	public Set<CustomerBook> getCustomerBooks() {
		return this.customerBooks;
	}

	public void addCustomerBook(CustomerBook customerBook) {
		this.customerBooks.add(customerBook);
	}

	@Override
	public boolean equals(Object other) {
		if (! (other instanceof Customer)) return false;
		return this.customerID.equals(((Customer)other).customerID);
	}

	@Override
	public int hashCode() {
		return this.customerID.hashCode();
	}

	@Override
	public String toString() {
		return this.customerID.toString();
	}
}