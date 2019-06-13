package fr.ubordeaux.ddd.cargoshippingsystem.domain.model.billing;

import java.util.Set;

/**
 * Repository interface
 * 
 */

public interface InvoiceRepository {
	public void store(Invoice invoice);
	public Set<Invoice> findAll();
	public Money findTotalMoneyCollected();
	public Invoice findByInvoiceID(InvoiceID invoiceID);
}