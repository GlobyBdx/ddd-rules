package fr.ubordeaux.ddd.example.domain.model.good.valueobjects;

import java.util.Objects;

import fr.ubordeaux.ddd.annotations.methods.Setter;
import fr.ubordeaux.ddd.annotations.types.ValueObject;

/**
 * Value Object 
 * 
 */

@ValueObject
public class GoodValueObjectE {
	private String value;

	public GoodValueObjectE(String value) {
		this.setValue(value);
	}

	@Setter
	private void setValue(String value) {
		if (value == null) {
			try {
				throw new Exception("Cannot create GoodValueObjectE with a null String.");
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		this.value = value;
	}

	@Override
	public boolean equals(Object other) {
		if (! (other instanceof GoodValueObjectE)) return false;
		return this.value.compareTo(((GoodValueObjectE)other).value) == 0;
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