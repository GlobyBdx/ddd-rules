package fr.ubordeaux.ddd.example.domain.model.evil.entities;

import fr.ubordeaux.ddd.annotations.fields.EntityID;
import fr.ubordeaux.ddd.annotations.methods.Getter;
import fr.ubordeaux.ddd.annotations.methods.Setter;
import fr.ubordeaux.ddd.example.domain.model.good.valueobjects.GoodValueObjectA;

/**
 * Entity
 * 
 */

public class EvilEntityWithoutEntityAnnotation {
	@EntityID
	private GoodValueObjectA valueObjectId;
	private String value;

	public EvilEntityWithoutEntityAnnotation(GoodValueObjectA valueObjectId) {
		this.setValueObjectID(valueObjectId);
	}

	@Setter
	private void setValueObjectID(GoodValueObjectA valueObjectId) {
		if (valueObjectId == null) {
			try {
				throw new Exception("Cannot create EvilEntityWithoutEntityAnnotation with a null GoodValueObjectA as ID.");
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		this.valueObjectId = valueObjectId;
	}

	public void setValue(String value) {
		this.value = value;
	}

	@Getter
	public GoodValueObjectA getValueObjectID() {
		return this.valueObjectId;
	}

	public String getValue() {
		return this.value;
	}

	@Override
	public boolean equals(Object other) {
		if (! (other instanceof EvilEntityWithoutEntityAnnotation)) return false;
		return this.valueObjectId.equals(((EvilEntityWithoutEntityAnnotation)other).valueObjectId);
	}

	@Override
	public int hashCode() {
		return this.valueObjectId.hashCode();
	}

	@Override
	public String toString() {
		return this.valueObjectId.toString();
	}
}