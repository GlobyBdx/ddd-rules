package fr.ubordeaux.ddd.example.domain.model.evil.entities;

import fr.ubordeaux.ddd.example.domain.model.good.valueobjects.GoodValueObjectA;

/**
 * Entity
 * 
 */

public class EvilEntityWithBadObjectClassMethodsA {
	private GoodValueObjectA valueObjectId;
	private String value;

	public EvilEntityWithBadObjectClassMethodsA(GoodValueObjectA valueObjectId) {
		this.setValueObjectID(valueObjectId);
	}

	private void setValueObjectID(GoodValueObjectA valueObjectId) {
		if (valueObjectId == null) {
			try {
				throw new Exception("Cannot create EvilEntityWithBadObjectClassMethodsA with a null GoodValueObjectA as ID.");
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

	public boolean equals(EvilEntityWithBadObjectClassMethodsA other) {
		return this.valueObjectId.equals(other.valueObjectId);
	}

	public int hashCode(int hashCode) {
		return hashCode;
	}

	public String toString(String toString) {
		return toString;
	}
}