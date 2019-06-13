package fr.ubordeaux.ddd.cargoshippingsystem.domain.model.location;

import java.util.Objects;

import fr.ubordeaux.ddd.annotations.types.ValueObject;

/**
 * Value Object
 * 
 */

@ValueObject
public class Port {
	private CityName cityName;
	private PortCode portCode;

	public Port(CityName cityName, PortCode portCode) {
		this.setCityName(cityName);
		this.setPortCode(portCode);
	}

	private void setCityName(CityName cityName)  {
		if (cityName == null) throw new LocationException("Cannot create Port with a null CityName.");
		this.cityName = cityName;
	}

	private void setPortCode(PortCode portCode)  {
		if (portCode == null) throw new LocationException("Cannot create Port with a null PortCode.");
		this.portCode = portCode;
	}

	public CityName getCityName()  {
		return this.cityName;
	}

	public PortCode getPortCode()  {
		return this.portCode;
	}

	@Override
	public boolean equals(Object other) {
		if (! (other instanceof Port)) return false;
		return (this.cityName.equals(((Port)other).cityName)
				&& this.portCode.equals(((Port)other).portCode));
	}

	@Override
	public int hashCode() {
		return Objects.hash(this.cityName.toString() + ":" + this.portCode.toString());
	}

	@Override
	public String toString() {
		return this.cityName.toString() + ":" + this.portCode.toString();
	}
}