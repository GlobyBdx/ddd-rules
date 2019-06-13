package fr.ubordeaux.ddd.cargoshippingsystem.userinterface;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import fr.ubordeaux.ddd.cargoshippingsystem.application.Booking;
import fr.ubordeaux.ddd.cargoshippingsystem.domain.model.billing.InvoiceID;
import fr.ubordeaux.ddd.cargoshippingsystem.domain.model.customer.CustomerID;
import fr.ubordeaux.ddd.cargoshippingsystem.domain.model.customer.CustomerRole;
import fr.ubordeaux.ddd.cargoshippingsystem.domain.model.location.LocationID;
import fr.ubordeaux.ddd.cargoshippingsystem.domain.model.shipping.CargoCustomer;
import fr.ubordeaux.ddd.cargoshippingsystem.domain.model.shipping.CargoTrackingID;

/**
 * Interface implementation
 * 
 */

public class BookingInterfaceImplementation implements BookingInterface {
	private Booking bookingApplication;

	public void setBookingApplication(Booking bookingApplication) {
		this.bookingApplication = bookingApplication;
	}

	@Override
	public String bookCargo(String invoiceID, Map<String, String> cargoCustomers, String locationID) {
		Set<CargoCustomer> customers = new HashSet<>();
		for (Map.Entry<String, String> cargoCustomer : cargoCustomers.entrySet()) {
			customers.add(new CargoCustomer(new CustomerID(cargoCustomer.getKey()), new CustomerRole(cargoCustomer.getValue())));
		}
		return this.bookingApplication.bookCargo(new InvoiceID(invoiceID), customers, new LocationID(locationID)).toString();
	}

	@Override
	public String bookAnotherCargo(String invoiceID, String cargoTrackingID, String locationID) {
		return this.bookingApplication.bookAnotherCargo(new InvoiceID(invoiceID), new CargoTrackingID(cargoTrackingID), new LocationID(locationID)).toString();
	}

	@Override
	public void cancelBookedCargo(String invoiceID, String cargoTrackingID) {
		this.bookingApplication.cancelBookedCargo(new InvoiceID(invoiceID), new CargoTrackingID(cargoTrackingID));
	}

	@Override
	public void changeBookedCargoDestination(String cargoTrackingID, String locationID) {
		this.bookingApplication.changeBookedCargoDestination(new CargoTrackingID(cargoTrackingID), new LocationID(locationID));
	}
}