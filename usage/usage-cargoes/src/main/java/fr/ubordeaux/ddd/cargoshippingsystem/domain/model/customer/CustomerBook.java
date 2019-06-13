package fr.ubordeaux.ddd.cargoshippingsystem.domain.model.customer;

import java.util.Objects;

import fr.ubordeaux.ddd.cargoshippingsystem.domain.model.billing.InvoiceID;
import fr.ubordeaux.ddd.cargoshippingsystem.domain.model.shipping.CargoTrackingID;
import fr.ubordeaux.ddd.annotations.types.ValueObject;

/**
 * Value Object 
 * 
 */

@ValueObject
public class CustomerBook {
	private CargoTrackingID cargoTrackingID;
	private InvoiceID invoiceID;

	public CustomerBook(CargoTrackingID cargoTrackingID, InvoiceID invoiceID) {
		this.setCargoTrackingID(cargoTrackingID);
		this.setInvoiceID(invoiceID);
	}

	private void setCargoTrackingID(CargoTrackingID cargoTrackingID)  {
		if (cargoTrackingID == null) throw new CustomerException("Cannot create CustomerBook with a null CargoTrackingID.");
		this.cargoTrackingID = cargoTrackingID;
	}

	private void setInvoiceID(InvoiceID customerRole)  {
		if (invoiceID == null) throw new CustomerException("Cannot create CustomerBook with a null InvoiceID.");
		this.invoiceID = customerRole;
	}

	public CargoTrackingID getCargoTrackingID()  {
		return this.cargoTrackingID;
	}

	public InvoiceID getInvoiceID()  {
		return this.invoiceID;
	}

	@Override
	public boolean equals(Object other) {
		if (! (other instanceof CustomerBook)) return false;
		return (this.cargoTrackingID.equals(((CustomerBook)other).cargoTrackingID)
				&& this.invoiceID.equals(((CustomerBook)other).invoiceID));
	}

	@Override
	public int hashCode() {
		return Objects.hash(this.cargoTrackingID.toString() + ":" + this.invoiceID.toString());
	}

	@Override
	public String toString() {
		return this.cargoTrackingID.toString() + ":" + this.invoiceID.toString();
	}

}