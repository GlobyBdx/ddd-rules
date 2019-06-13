package fr.ubordeaux.ddd.cargoshippingsystem.infrastructure.persistence.inmemory;

import java.util.HashSet;
import java.util.Set;

import fr.ubordeaux.ddd.cargoshippingsystem.domain.model.location.CityName;
import fr.ubordeaux.ddd.cargoshippingsystem.domain.model.location.Location;
import fr.ubordeaux.ddd.cargoshippingsystem.domain.model.location.LocationID;
import fr.ubordeaux.ddd.cargoshippingsystem.domain.model.location.LocationRepository;
import fr.ubordeaux.ddd.cargoshippingsystem.domain.model.location.Port;
import fr.ubordeaux.ddd.cargoshippingsystem.domain.model.location.PortCode;
import fr.ubordeaux.ddd.annotations.types.Repository;

/**
 * Repository implementation
 * 
 */

@Repository
public class LocationRepositoryImplementation implements LocationRepository {
	private Set<Location> locations;

	public LocationRepositoryImplementation() {
		this.locations = new HashSet<>();
	}

	@Override
	public void store(Location location) {
		if (this.findAll().contains(location)) {
			this.locations.remove(location);
		}
		this.locations.add(location);
	}

	@Override
	public Set<Location> findAll() {
		return this.locations;
	}

	@Override
	public Location findByLocationID(LocationID locationID) {
		for (Location location : this.findAll()) {
			if (location.getLocationID().equals(locationID)) {
				return location;
			}
		}
		return null;
	}

	@Override
	public Location findByPort(Port port) {
		for (Location location : this.findAll()) {
			if (location.getPort().equals(port)) {
				return location;
			}
		}
		return null;
	}

	@Override
	public Location findByCityName(CityName cityName) {
		for (Location location : this.findAll()) {
			if (location.getPort().getCityName().equals(cityName)) {
				return location;
			}
		}
		return null;
	}

	@Override
	public Location findByPortCode(PortCode portCode) {
		for (Location location : this.findAll()) {
			if (location.getPort().getPortCode().equals(portCode)) {
				return location;
			}
		}
		return null;
	}
}