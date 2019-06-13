package fr.ubordeaux.ddd.cargoshippingsystem.application;

import fr.ubordeaux.ddd.cargoshippingsystem.domain.model.billing.Invoice;
import fr.ubordeaux.ddd.cargoshippingsystem.domain.model.billing.InvoiceID;
import fr.ubordeaux.ddd.cargoshippingsystem.domain.model.billing.InvoiceRepository;
import fr.ubordeaux.ddd.cargoshippingsystem.domain.model.billing.Money;
import fr.ubordeaux.ddd.cargoshippingsystem.domain.model.billing.State;
import fr.ubordeaux.ddd.annotations.types.Service;

/**
 * Service implementation
 * 
 */

@Service
public class AddingInvoiceImplementation implements AddingInvoice {
	private final InvoiceRepository invoiceRepository;

	public AddingInvoiceImplementation(InvoiceRepository invoiceRepository) {
		this.invoiceRepository = invoiceRepository;
	}

	@Override
	public void addInvoice(InvoiceID invoiceID) {
		this.invoiceRepository.store(new Invoice(invoiceID));
	}

	@Override
	public void addInvoice(InvoiceID invoiceID, Money money, State state) {
		Invoice invoice = new Invoice(invoiceID);
		invoice.setMoney(money);
		invoice.setState(state);
		this.invoiceRepository.store(invoice);
	}
}