package fr.ubordeaux.ddd.cargoshippingsystem.domain.model.billing;

/**
 * Exception
 * 
 */

public class InvoiceException extends RuntimeException {
	private static final long serialVersionUID = 1L;

	public InvoiceException(String message) {
		super(message);
	}
}