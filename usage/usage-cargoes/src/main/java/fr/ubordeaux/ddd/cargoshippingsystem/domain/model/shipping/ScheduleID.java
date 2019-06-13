package fr.ubordeaux.ddd.cargoshippingsystem.domain.model.shipping;

import java.util.Objects;

import fr.ubordeaux.ddd.annotations.types.ValueObject;

/**
 * Value Object 
 * 
 */

@ValueObject
public class ScheduleID {
	private String value;

	public ScheduleID(String value) {
		this.setValue(value);
	}

	private void setValue(String value)  {
		if (value == null) throw new CarrierMovementException("Cannot create ScheduleID with a null value.");
		this.value = value;
	}

	@Override
	public boolean equals(Object other) {
		if (! (other instanceof ScheduleID)) return false;
		return this.value.compareTo(((ScheduleID)other).value) == 0;
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