package fr.ubordeaux.ddd.example.domain.model.evil.valueobjects;

import java.util.Objects;

import fr.ubordeaux.ddd.example.domain.model.good.valueobjects.GoodValueObjectA;

/**
 * Value Object 
 * 
 */

public class GoodValueObjectWithPublicSetter {
	private GoodValueObjectA valueObject;
	private String value;

	public GoodValueObjectWithPublicSetter(GoodValueObjectA valueObject, String value) {
		this.setValueObject(valueObject);
		this.setValue(value);
	}

	private void setValueObject(GoodValueObjectA valueObject) {
		if (valueObject == null) {
			try {
				throw new Exception("Cannot create GoodValueObjectWithPublicSetter with a null GoodValueObjectA.");
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		this.valueObject = valueObject;
	}

	public void accessibleSetValueObject(GoodValueObjectA valueObject) {
		setValueObject(valueObject);
	}

	private void setValue(String value) {
		if (value == null) {
			try {
				throw new Exception("Cannot create GoodValueObjectWithPublicSetter with a null String.");
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		this.value = value;
	}

	public void accessibleSetValue(String value) {
		setValue(value);
	}

	public GoodValueObjectA getValueObject() {
		return this.valueObject;
	}

	@Override
	public boolean equals(Object other) {
		if (! (other instanceof GoodValueObjectWithPublicSetter)) return false;
		return (this.valueObject.equals(((GoodValueObjectWithPublicSetter)other).valueObject)
				&& this.value.compareTo(((GoodValueObjectWithPublicSetter)other).value) == 0);
	}

	@Override
	public int hashCode() {
		return this.valueObject.hashCode() + Objects.hash(this.value);
	}

	@Override
	public String toString() {
		return this.valueObject.toString() + this.value;
	}
}