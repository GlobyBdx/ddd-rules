package fr.ubordeaux.ddd.cargoshippingsystem.domain.model.customer;

import java.util.Objects;

import fr.ubordeaux.ddd.annotations.types.ValueObject;

/**
 * Value Object 
 * 
 */

@ValueObject
public class CustomerID {
	private String value;

	public CustomerID(String value) {
		this.setValue(value);
	}

	private void setValue(String value)  {
		if (value == null) throw new CustomerException("Cannot create CustomerID with a null value.");
		this.value = value;
	}

	@Override
	public boolean equals(Object other) {
		if (! (other instanceof CustomerID)) return false;
		return this.value.compareTo(((CustomerID)other).value) == 0;
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