package fr.ubordeaux.ddd.example.domain.model.good.entities;

import fr.ubordeaux.ddd.example.domain.model.good.valueobjects.GoodValueObjectB;
import fr.ubordeaux.ddd.example.domain.model.good.valueobjects.GoodValueObjectE;

/**
 * Entity
 * 
 */

public class GoodEntityC {
	private GoodValueObjectE valueObjectId;
	private GoodValueObjectB valueObject;
	private String value;

	public GoodEntityC(GoodValueObjectE valueObjectId) {
		this.setValueObjectID(valueObjectId);
	}

	private void setValueObjectID(GoodValueObjectE valueObjectId) {
		if (valueObjectId == null) {
			try {
				throw new Exception("Cannot create GoodEntityC with a null GoodValueObjectE as ID.");
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

	public GoodValueObjectE getValueObjectID() {
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
		if (! (other instanceof GoodEntityC)) return false;
		return this.valueObjectId.equals(((GoodEntityC)other).valueObjectId);
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