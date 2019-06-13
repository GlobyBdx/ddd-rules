package fr.ubordeaux.ddd.cargoshippingsystem.domain.model.location;

import java.util.Set;

/**
 * Repository interface
 * 
 */

public interface LocationRepository {
	public void store(Location location);
	public Set<Location> findAll();
	public Location findByLocationID(LocationID locationID);
	public Location findByPort(Port port);
	public Location findByCityName(CityName cityName);
	public Location findByPortCode(PortCode portCode);
}