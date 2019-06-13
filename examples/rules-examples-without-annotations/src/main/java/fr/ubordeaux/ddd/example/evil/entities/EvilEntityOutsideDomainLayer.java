package fr.ubordeaux.ddd.example.evil.entities;

import fr.ubordeaux.ddd.example.domain.model.good.valueobjects.GoodValueObjectA;

/**
 * Entity
 * 
 */

public class EvilEntityOutsideDomainLayer {
	private GoodValueObjectA valueObjectId;
	private String value;

	public EvilEntityOutsideDomainLayer(GoodValueObjectA valueObjectId) {
		this.setValueObjectID(valueObjectId);
	}

	private void setValueObjectID(GoodValueObjectA valueObjectId) {
		if (valueObjectId == null) {
			try {
				throw new Exception("Cannot create EvilEntityOutsideDomainLayer with a null GoodValueObjectA as ID.");
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
		if (! (other instanceof EvilEntityOutsideDomainLayer)) return false;
		return this.valueObjectId.equals(((EvilEntityOutsideDomainLayer)other).valueObjectId);
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