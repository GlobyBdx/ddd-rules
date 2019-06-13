package fr.ubordeaux.ddd.cargoshippingsystem.domain.model.billing;

import fr.ubordeaux.ddd.annotations.types.Entity;
import fr.ubordeaux.ddd.annotations.fields.EntityID;

/**
 * Entity
 * 
 */

@Entity
public class Invoice {
	@EntityID
	private InvoiceID invoiceID;
	private Money money;
	private State state;

	public Invoice(InvoiceID invoiceID) {
		this.setInvoiceID(invoiceID);
	}

	private void setInvoiceID(InvoiceID invoiceID) {
		if (invoiceID == null) throw new InvoiceException("Cannot create Invoice with a null InvoiceID.");
		this.invoiceID = invoiceID;
	}

	public void setMoney(Money money) {
		this.money = money;
	}

	public void setState(State state) {
		this.state = state;
	}

	public InvoiceID getInvoiceID() {
		return this.invoiceID;
	}

	public Money getMoney() {
		return this.money;
	}

	public State getState() {
		return this.state;
	}

	@Override
	public boolean equals(Object other) {
		if (! (other instanceof Invoice)) return false;
		return this.invoiceID.equals(((Invoice)other).invoiceID);
	}

	@Override
	public int hashCode() {
		return this.invoiceID.hashCode();
	}

	@Override
	public String toString() {
		return this.invoiceID.toString();
	}


}