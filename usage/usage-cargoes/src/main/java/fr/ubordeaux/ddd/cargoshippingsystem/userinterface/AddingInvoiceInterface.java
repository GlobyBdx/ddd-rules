package fr.ubordeaux.ddd.cargoshippingsystem.userinterface;

/**
 * User interface
 * 
 */

public interface AddingInvoiceInterface {
	public void addInvoice(String invoiceID);
	public void addInvoice(String invoiceID, double price, String currency);
}