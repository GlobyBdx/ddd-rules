package fr.ubordeaux.ddd.cargoshippingsystem.domain.model.customer;

import java.util.Objects;

import fr.ubordeaux.ddd.annotations.types.ValueObject;

/**
 * Value Object 
 * 
 */

@ValueObject
public class CustomerName {
	private String value;

	public CustomerName(String value) {
		this.setValue(value);
	}

	private void setValue(String value)  {
		if (value == null) throw new CustomerException("Cannot create CustomerName with a null value.");
		this.value = value;
	}

	@Override
	public boolean equals(Object other) {
		if (! (other instanceof CustomerName)) return false;
		return this.value.compareTo(((CustomerName)other).value) == 0;
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