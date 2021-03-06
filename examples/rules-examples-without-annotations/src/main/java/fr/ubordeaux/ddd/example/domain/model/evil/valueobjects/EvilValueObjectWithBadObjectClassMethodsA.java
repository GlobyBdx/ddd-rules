package fr.ubordeaux.ddd.example.domain.model.evil.valueobjects;

import fr.ubordeaux.ddd.example.domain.model.good.valueobjects.GoodValueObjectA;

/**
 * Value Object 
 * 
 */

public class EvilValueObjectWithBadObjectClassMethodsA {
	private GoodValueObjectA valueObject;
	private String value;

	public EvilValueObjectWithBadObjectClassMethodsA(GoodValueObjectA valueObject, String value) {
		this.setValueObject(valueObject);
		this.setValue(value);
	}

	private void setValueObject(GoodValueObjectA valueObject) {
		if (valueObject == null) {
			try {
				throw new Exception("Cannot create EvilValueObjectWithBadObjectClassMethodsA with a null GoodValueObjectA.");
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		this.valueObject = valueObject;
	}

	private void setValue(String value) {
		if (value == null) {
			try {
				throw new Exception("Cannot create EvilValueObjectWithBadObjectClassMethodsA with a null String.");
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		this.value = value;
	}

	public GoodValueObjectA getValueObject() {
		return this.valueObject;
	}

	public boolean equals(EvilValueObjectWithBadObjectClassMethodsA other) {
		return (this.valueObject.equals(other.valueObject)
				&& this.value.compareTo(other.value) == 0);
	}

	public int hashCode(int hashCode) {
		return hashCode;
	}

	public String toString(String toString) {
		return toString;
	}
}