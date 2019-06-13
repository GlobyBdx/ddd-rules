package fr.ubordeaux.ddd.cargoshippingsystem.domain.model.shipping;

import java.util.Objects;

import fr.ubordeaux.ddd.annotations.types.ValueObject;

/**
 * Value Object 
 * 
 */

@ValueObject
public class CargoTrackingID {
	private String value;

	public CargoTrackingID(String value) {
		this.setValue(value);
	}

	private void setValue(String value)  {
		if (value == null) throw new CargoException("Cannot create CargoTrackingID with a null value.");
		this.value = value;
	}

	@Override
	public boolean equals(Object other) {
		if (! (other instanceof CargoTrackingID)) return false;
		return this.value.compareTo(((CargoTrackingID)other).value) == 0;
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