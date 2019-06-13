package fr.ubordeaux.ddd.cargoshippingsystem.application;

import fr.ubordeaux.ddd.cargoshippingsystem.domain.model.billing.InvoiceID;
import fr.ubordeaux.ddd.cargoshippingsystem.domain.model.billing.Money;
import fr.ubordeaux.ddd.cargoshippingsystem.domain.model.billing.State;

/**
 * Service interface
 * 
 */

public interface AddingInvoice {
	public void addInvoice(InvoiceID invoiceID);
	public void addInvoice(InvoiceID invoiceID, Money money, State state);
}