package fr.ubordeaux.ddd.cargoshippingsystem.userinterface;

import fr.ubordeaux.ddd.cargoshippingsystem.application.AddingLocation;
import fr.ubordeaux.ddd.cargoshippingsystem.domain.model.location.CityName;
import fr.ubordeaux.ddd.cargoshippingsystem.domain.model.location.LocationID;
import fr.ubordeaux.ddd.cargoshippingsystem.domain.model.location.Port;
import fr.ubordeaux.ddd.cargoshippingsystem.domain.model.location.PortCode;

/**
 * Interface implementation
 * 
 */

public class AddingLocationInterfaceImplementation implements AddingLocationInterface {
	private AddingLocation addingLocation;

	public void setAddingLocation(AddingLocation addingLocation) {
		this.addingLocation = addingLocation;
	}

	@Override
	public void addLocation(String locationID) {
		this.addingLocation.addLocation(new LocationID(locationID));
	}

	@Override
	public void addLocation(String locationID, String cityName, String portCode) {
		this.addingLocation.addLocation(new LocationID(locationID), new Port(new CityName(cityName), new PortCode(portCode)));
	}
}