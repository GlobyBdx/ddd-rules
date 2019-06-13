package fr.ubordeaux.ddd.example.domain.model.evil.valueobjects;

import fr.ubordeaux.ddd.annotations.types.ValueObject;

/**
 * Value Object 
 * 
 */

@ValueObject
public class EvilValueObjectWithoutField {
	public EvilValueObjectWithoutField() {}

	@Override
	public boolean equals(Object other) {
		return true;
	}

	@Override
	public int hashCode() {
		return 0;
	}

	@Override
	public String toString() {
		return "";
	}
}