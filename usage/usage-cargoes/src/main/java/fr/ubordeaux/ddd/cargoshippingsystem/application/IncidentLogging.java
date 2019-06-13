package fr.ubordeaux.ddd.cargoshippingsystem.application;

import fr.ubordeaux.ddd.cargoshippingsystem.domain.model.location.LocationID;
import fr.ubordeaux.ddd.cargoshippingsystem.domain.model.shipping.CargoTrackingID;

/**
 * Service interface
 * 
 */

public interface IncidentLogging {
	public void cargoHasBeenSent(CargoTrackingID cargoTrackingID, LocationID fromLocationID, LocationID toLocationID);
	public void cargoHasBeenReceived(CargoTrackingID cargoTrackingID);
}