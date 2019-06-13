package fr.ubordeaux.ddd.example.domain.model.evil.entities;

import fr.ubordeaux.ddd.example.domain.model.good.valueobjects.GoodValueObjectA;

/**
 * Entity
 * 
 */

public class EvilEntityWithPublicEntityIdSetter {
	private GoodValueObjectA valueObjectId;
	private String value;

	public EvilEntityWithPublicEntityIdSetter(GoodValueObjectA valueObjectId) {
		this.setValueObjectID(valueObjectId);
	}

	public void setValueObjectID(GoodValueObjectA valueObjectId) {
		if (valueObjectId == null) {
			try {
				throw new Exception("Cannot create EvilEntityWithPublicEntityIdSetter with a null GoodValueObjectA as ID.");
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		this.valueObjectId = valueObjectId;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public GoodValueObjectA getValueObjectID() {
		return this.valueObjectId;
	}

	public String getValue() {
		return this.value;
	}

	@Override
	public boolean equals(Object other) {
		if (! (other instanceof EvilEntityWithPublicEntityIdSetter)) return false;
		return this.valueObjectId.equals(((EvilEntityWithPublicEntityIdSetter)other).valueObjectId);
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