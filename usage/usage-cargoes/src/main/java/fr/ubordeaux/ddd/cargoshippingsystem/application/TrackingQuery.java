package fr.ubordeaux.ddd.cargoshippingsystem.application;

import fr.ubordeaux.ddd.cargoshippingsystem.domain.model.shipping.CargoTrackingID;
import fr.ubordeaux.ddd.cargoshippingsystem.domain.model.shipping.HandlingEvent;

/**
 * Service interface
 * 
 */

public interface TrackingQuery {
	public HandlingEvent inspectCargo(CargoTrackingID cargoTrackingID);
}