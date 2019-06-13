package fr.ubordeaux.ddd.cargoshippingsystem.userinterface;

import java.util.Map;

/**
 * User interface
 * 
 */

public interface BookingInterface {
	public String bookCargo(String invoiceID, Map<String, String> cargoCustomers, String locationID);
	public String bookAnotherCargo(String invoiceID, String cargoTrackingID, String locationID);
	public void cancelBookedCargo(String invoiceID, String cargoTrackingID);
	public void changeBookedCargoDestination(String cargoTrackingID, String locationID);
}