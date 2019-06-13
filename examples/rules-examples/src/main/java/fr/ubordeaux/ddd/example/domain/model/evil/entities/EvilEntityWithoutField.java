package fr.ubordeaux.ddd.example.domain.model.evil.entities;

import fr.ubordeaux.ddd.annotations.types.Entity;

/**
 * Entity
 * 
 */

@Entity
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