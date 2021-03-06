package fr.ubordeaux.ddd.example.domain.model.good.entities;

import fr.ubordeaux.ddd.example.domain.model.good.valueobjects.GoodValueObjectB;
import fr.ubordeaux.ddd.example.domain.model.good.valueobjects.GoodValueObjectD;

/**
 * Entity
 * 
 */

public class GoodEntityB {
	private GoodValueObjectD valueObjectId;
	private GoodValueObjectB valueObject;
	private String value;

	public GoodEntityB(GoodValueObjectD valueObjectId) {
		this.setValueObjectID(valueObjectId);
	}

	private void setValueObjectID(GoodValueObjectD valueObjectId) {
		if (valueObjectId == null) {
			try {
				throw new Exception("Cannot create GoodEntityB with a null GoodValueObjectD as ID.");
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		this.valueObjectId = valueObjectId;
	}

	public void setValueObject(GoodValueObjectB valueObject) {
		this.valueObject = valueObject;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public GoodValueObjectD getValueObjectID() {
		return this.valueObjectId;
	}

	public GoodValueObjectB getValueObject() {
		return this.valueObject;
	}

	public String getValue() {
		return this.value;
	}

	@Override
	public boolean equals(Object other) {
		if (! (other instanceof GoodEntityB)) return false;
		return this.valueObjectId.equals(((GoodEntityB)other).valueObjectId);
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