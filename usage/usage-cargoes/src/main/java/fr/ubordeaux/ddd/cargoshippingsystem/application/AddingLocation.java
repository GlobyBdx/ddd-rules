package fr.ubordeaux.ddd.cargoshippingsystem.application;

import fr.ubordeaux.ddd.cargoshippingsystem.domain.model.location.LocationID;
import fr.ubordeaux.ddd.cargoshippingsystem.domain.model.location.Port;

/**
 * Service interface
 * 
 */

public interface AddingLocation {
	public void addLocation(LocationID locationID);
	public void addLocation(LocationID locationID, Port port);
}