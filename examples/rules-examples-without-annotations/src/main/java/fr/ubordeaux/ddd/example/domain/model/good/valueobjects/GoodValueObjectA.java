package fr.ubordeaux.ddd.example.domain.model.good.valueobjects;

import java.util.Objects;

/**
 * Value Object 
 * 
 */

public class GoodValueObjectA {
	private String value;

	public GoodValueObjectA(String value) {
		this.setValue(value);
	}

	private void setValue(String value) {
		if (value == null) {
			try {
				throw new Exception("Cannot create GoodValueObjectA with a null String.");
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		this.value = value;
	}

	@Override
	public boolean equals(Object other) {
		if (! (other instanceof GoodValueObjectA)) return false;
		return this.value.compareTo(((GoodValueObjectA)other).value) == 0;
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