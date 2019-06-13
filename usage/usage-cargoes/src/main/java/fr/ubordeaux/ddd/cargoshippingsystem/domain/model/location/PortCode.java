package fr.ubordeaux.ddd.cargoshippingsystem.domain.model.location;

import java.util.Objects;

import fr.ubordeaux.ddd.annotations.types.ValueObject;

/**
 * Value Object 
 * 
 */

@ValueObject
public class PortCode {
	private String value;

	public PortCode(String value) {
		this.setValue(value);
	}

	private void setValue(String value)  {
		if (value == null) throw new LocationException("Cannot create PortCode with a null value.");
		this.value = value;
	}

	@Override
	public boolean equals(Object other) {
		if (! (other instanceof PortCode)) return false;
		return this.value.compareTo(((PortCode)other).value) == 0;
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