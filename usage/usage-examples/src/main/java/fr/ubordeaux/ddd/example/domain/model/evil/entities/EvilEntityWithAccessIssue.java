package fr.ubordeaux.ddd.example.domain.model.evil.entities;

import fr.ubordeaux.ddd.annotations.fields.EntityID;
import fr.ubordeaux.ddd.annotations.methods.Setter;
import fr.ubordeaux.ddd.annotations.types.Entity;

/**
 * Entity
 * 
 */

@Entity
public class EvilEntityWithAccessIssue {
	@EntityID
	private String valueId;
	private String value;

	public EvilEntityWithAccessIssue(String valueId) {
		this.setValueID(valueId);
	}

	@Setter
	private void setValueID(String valueId) {
		if (valueId == null) {
			try {
				throw new Exception("Cannot create EvilEntityWithAccessIssue with a null String as ID.");
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		this.valueId = valueId;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getValue() {
		return this.value;
	}

	public void accessFields() {
		this.valueId.getClass();
	}

	@Override
	public boolean equals(Object other) {
		if (! (other instanceof EvilEntityWithAccessIssue)) return false;
		return this.valueId.compareTo(((EvilEntityWithAccessIssue)other).valueId) == 0;
	}

	@Override
	public int hashCode() {
		return this.valueId.hashCode();
	}

	@Override
	public String toString() {
		return this.valueId;
	}
}