package fr.ubordeaux.ddd.cargoshippingsystem.userinterface;

import fr.ubordeaux.ddd.cargoshippingsystem.application.AddingInvoice;
import fr.ubordeaux.ddd.cargoshippingsystem.domain.model.billing.Currency;
import fr.ubordeaux.ddd.cargoshippingsystem.domain.model.billing.InvoiceID;
import fr.ubordeaux.ddd.cargoshippingsystem.domain.model.billing.Money;
import fr.ubordeaux.ddd.cargoshippingsystem.domain.model.billing.Price;
import fr.ubordeaux.ddd.cargoshippingsystem.domain.model.billing.State;

/**
 * Interface implementation
 * 
 */

public class AddingInvoiceInterfaceImplementation implements AddingInvoiceInterface {
	private AddingInvoice addingInvoice;

	public void setAddingInvoice(AddingInvoice addingInvoice) {
		this.addingInvoice = addingInvoice;
	}

	@Override
	public void addInvoice(String invoiceID) {
		this.addingInvoice.addInvoice(new InvoiceID(invoiceID));
	}

	@Override
	public void addInvoice(String invoiceID, double price, String currency) {
		this.addingInvoice.addInvoice(new InvoiceID(invoiceID), new Money(new Price(price), new Currency(currency)), State.NONE);
	}
}