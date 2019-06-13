package fr.ubordeaux.ddd.example.domain.model.evil.valueobjects;

/**
 * Value Object 
 * 
 */

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