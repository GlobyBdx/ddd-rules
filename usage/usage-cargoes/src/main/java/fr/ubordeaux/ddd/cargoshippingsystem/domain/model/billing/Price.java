package fr.ubordeaux.ddd.cargoshippingsystem.domain.model.billing;

import java.util.Objects;

import fr.ubordeaux.ddd.annotations.types.ValueObject;

/**
 * Value Object 
 * 
 */

@ValueObject
public class Price {
	private double value;

	public Price(double value) {
		this.setValue(value);
	}

	private void setValue(double value)  {
		this.value = value;
	}

	@Override
	public boolean equals(Object other) {
		if (! (other instanceof Currency)) return false;
		return this.value == value;
	}

	@Override
	public int hashCode() {
		return Objects.hash(this.value);
	}

	@Override
	public String toString() {
		return String.valueOf(this.value);
	}
}