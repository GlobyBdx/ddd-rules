package fr.ubordeaux.ddd.cargoshippingsystem.domain.model.location;

import fr.ubordeaux.ddd.annotations.types.Entity;
import fr.ubordeaux.ddd.annotations.fields.EntityID;

/**
 * Entity
 * 
 */

@Entity
public class Location {
	@EntityID
	private LocationID locationID;
	private Port port;

	public Location(LocationID locationID) {
		this.setLocationID(locationID);
	}

	private void setLocationID(LocationID locationID) {
		if (locationID == null) throw new LocationException("Cannot create Location with a null LocationID.");
		this.locationID = locationID;
	}

	public void setPort(Port port) {
		this.port = port;
	}

	public LocationID getLocationID() {
		return this.locationID;
	}
	public Port getPort() {
		return this.port;
	}

	@Override
	public boolean equals(Object other) {
		if (! (other instanceof Location)) return false;
		return this.locationID.equals(((Location)other).locationID);
	}

	@Override
	public int hashCode() {
		return this.locationID.hashCode();
	}

	@Override
	public String toString() {
		return this.locationID.toString();
	}
}