package fr.ubordeaux.ddd.cargoshippingsystem.application;

import java.util.Set;

import fr.ubordeaux.ddd.cargoshippingsystem.domain.model.billing.InvoiceID;
import fr.ubordeaux.ddd.cargoshippingsystem.domain.model.location.LocationID;
import fr.ubordeaux.ddd.cargoshippingsystem.domain.model.shipping.CargoCustomer;
import fr.ubordeaux.ddd.cargoshippingsystem.domain.model.shipping.CargoTrackingID;

/**
 * Service interface
 * 
 */

public interface Booking {
	public CargoTrackingID bookCargo(InvoiceID invoiceID, Set<CargoCustomer> cargoCustomers, LocationID locationID);
	public CargoTrackingID bookAnotherCargo(InvoiceID invoiceID, CargoTrackingID cargoTrackingID, LocationID locationID);
	public void cancelBookedCargo(InvoiceID invoiceID, CargoTrackingID cargoTrackingID);
	public void changeBookedCargoDestination(CargoTrackingID cargoTrackingID, LocationID locationID);
}