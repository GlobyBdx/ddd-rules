package fr.ubordeaux.ddd.cargoshippingsystem.application;

import fr.ubordeaux.ddd.cargoshippingsystem.domain.model.location.Location;
import fr.ubordeaux.ddd.cargoshippingsystem.domain.model.location.LocationID;
import fr.ubordeaux.ddd.cargoshippingsystem.domain.model.location.LocationRepository;
import fr.ubordeaux.ddd.cargoshippingsystem.domain.model.location.Port;
import fr.ubordeaux.ddd.annotations.types.Service;

/**
 * Service implementation
 * 
 */

@Service
public class AddingLocationImplementation implements AddingLocation {
	private final LocationRepository locationRepository;

	public AddingLocationImplementation(LocationRepository locationRepository) {
		this.locationRepository = locationRepository;
	}

	@Override
	public void addLocation(LocationID locationID) {
		this.locationRepository.store(new Location(locationID));
	}

	@Override
	public void addLocation(LocationID locationID, Port port) {
		Location location = new Location(locationID);
		location.setPort(port);
		this.locationRepository.store(location);
	}
}