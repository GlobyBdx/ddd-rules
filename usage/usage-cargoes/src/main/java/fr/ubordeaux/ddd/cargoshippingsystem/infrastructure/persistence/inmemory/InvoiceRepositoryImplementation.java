package fr.ubordeaux.ddd.cargoshippingsystem.infrastructure.persistence.inmemory;

import java.util.HashSet;
import java.util.Set;

import fr.ubordeaux.ddd.cargoshippingsystem.domain.model.billing.Invoice;
import fr.ubordeaux.ddd.cargoshippingsystem.domain.model.billing.InvoiceID;
import fr.ubordeaux.ddd.cargoshippingsystem.domain.model.billing.InvoiceRepository;
import fr.ubordeaux.ddd.cargoshippingsystem.domain.model.billing.Money;
import fr.ubordeaux.ddd.annotations.types.Repository;

/**
 * Repository implementation
 * 
 */

@Repository
public class InvoiceRepositoryImplementation implements InvoiceRepository {
	private Set<Invoice> invoices;

	public InvoiceRepositoryImplementation() {
		this.invoices = new HashSet<>();
	}

	@Override
	public void store(Invoice invoice) {
		if (this.findAll().contains(invoice)) {
			this.invoices.remove(invoice);
		}
		this.invoices.add(invoice);
	}

	@Override
	public Set<Invoice> findAll() {
		return invoices;
	}

	@Override
	public Money findTotalMoneyCollected() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Invoice findByInvoiceID(InvoiceID invoiceID) {
		for (Invoice invoice : this.findAll()) {
			if (invoice.getInvoiceID().equals(invoiceID)) {
				return invoice;
			}
		}
		return null;
	}
}