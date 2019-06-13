package fr.ubordeaux.ddd.cargoshippingsystem.userinterface;

import fr.ubordeaux.ddd.cargoshippingsystem.application.TrackingQuery;
import fr.ubordeaux.ddd.cargoshippingsystem.domain.model.shipping.CargoTrackingID;

/**
 * Interface implementation
 * 
 */

public class TrackingQueryInterfaceImplementation implements TrackingQueryInterface {
	private TrackingQuery trackingQuery;

	public void setTrackingQuery(TrackingQuery trackingQuery) {
		this.trackingQuery = trackingQuery;
	}

	@Override
	public String inspectCargo(String cargoTrackingID) {
		return this.trackingQuery.inspectCargo(new CargoTrackingID(cargoTrackingID)).toString();
	}
}