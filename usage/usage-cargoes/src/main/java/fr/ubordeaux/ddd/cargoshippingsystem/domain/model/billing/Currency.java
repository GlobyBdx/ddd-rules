package fr.ubordeaux.ddd.cargoshippingsystem.domain.model.billing;

import java.util.Objects;

import fr.ubordeaux.ddd.annotations.types.ValueObject;

/**
 * Value Object 
 * 
 */

@ValueObject
public class Currency {
	private String value;

	public Currency(String value) {
		this.setValue(value);
	}

	private void setValue(String value)  {
		if (value == null) throw new InvoiceException("Cannot create Currency with a null value.");
		this.value = value;
	}

	@Override
	public boolean equals(Object other) {
		if (! (other instanceof Currency)) return false;
		return this.value.compareTo(((Currency)other).value) == 0;
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