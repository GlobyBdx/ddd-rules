package fr.ubordeaux.ddd.example.domain.model.evil.entities;

/**
 * Entity
 * 
 */

public class EvilEntityWithoutField {
	public EvilEntityWithoutField() {}

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