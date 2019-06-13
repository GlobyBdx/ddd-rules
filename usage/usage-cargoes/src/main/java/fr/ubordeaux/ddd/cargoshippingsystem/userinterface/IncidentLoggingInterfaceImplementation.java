package fr.ubordeaux.ddd.cargoshippingsystem.userinterface;

import fr.ubordeaux.ddd.cargoshippingsystem.application.IncidentLogging;
import fr.ubordeaux.ddd.cargoshippingsystem.domain.model.location.LocationID;
import fr.ubordeaux.ddd.cargoshippingsystem.domain.model.shipping.CargoTrackingID;

/**
 * Interface implementation
 * 
 */

public class IncidentLoggingInterfaceImplementation implements IncidentLoggingInterface {
	private IncidentLogging incidentLoggingApplication;

	public void setIncidentLoggingApplication(IncidentLogging incidentLoggingApplication) {
		this.incidentLoggingApplication = incidentLoggingApplication;
	}

	@Override
	public void cargoHasBeenSent(String cargoTrackingID, String fromLocationID, String toLocationID) {
		this.incidentLoggingApplication.cargoHasBeenSent(new CargoTrackingID(cargoTrackingID), new LocationID(fromLocationID), new LocationID(toLocationID));
	}

	@Override
	public void cargoHasBeenReceived(String cargoTrackingID) {
		this.incidentLoggingApplication.cargoHasBeenReceived(new CargoTrackingID(cargoTrackingID));
	}
}