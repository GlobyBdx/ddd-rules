package fr.ubordeaux.ddd.cargoshippingsystem.domain.model.billing;

import java.util.Objects;

import fr.ubordeaux.ddd.annotations.types.ValueObject;

/**
 * Value Object 
 * 
 */

@ValueObject
public class InvoiceID {
	private String value;

	public InvoiceID(String value) {
		this.setValue(value);
	}

	private void setValue(String value)  {
		if (value == null) throw new InvoiceException("Cannot create InvoiceID with a null value.");
		this.value = value;
	}

	@Override
	public boolean equals(Object other) {
		if (! (other instanceof InvoiceID)) return false;
		return this.value.compareTo(((InvoiceID)other).value) == 0;
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