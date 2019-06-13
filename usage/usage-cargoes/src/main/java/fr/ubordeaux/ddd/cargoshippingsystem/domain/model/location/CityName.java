package fr.ubordeaux.ddd.cargoshippingsystem.domain.model.location;

import java.util.Objects;

import fr.ubordeaux.ddd.annotations.types.ValueObject;

/**
 * Value Object 
 * 
 */

@ValueObject
public class CityName {
	private String value;

	public CityName(String value) {
		this.setValue(value);
	}

	private void setValue(String value)  {
		if (value == null) throw new LocationException("Cannot create CityName with a null value.");
		this.value = value;
	}

	@Override
	public boolean equals(Object other) {
		if (! (other instanceof CityName)) return false;
		return this.value.compareTo(((CityName)other).value) == 0;
	}

	@Override
	public int hashCode() {
		return Objects.hash(this.value);
	}

	@Override
	public String toString() {
		return this.value;
	}
}