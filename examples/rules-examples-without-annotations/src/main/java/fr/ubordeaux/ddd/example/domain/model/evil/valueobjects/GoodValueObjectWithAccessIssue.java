package fr.ubordeaux.ddd.example.domain.model.evil.valueobjects;

import java.util.Objects;

import fr.ubordeaux.ddd.example.domain.model.good.valueobjects.GoodValueObjectA;

/**
 * Value Object 
 * 
 */

public class GoodValueObjectWithAccessIssue {
	private GoodValueObjectA valueObject;
	private String value;

	public GoodValueObjectWithAccessIssue(GoodValueObjectA valueObject, String value) {
		this.setValueObject(valueObject);
		this.setValue(value);
	}

	private void setValueObject(GoodValueObjectA valueObject) {
		if (valueObject == null) {
			try {
				throw new Exception("Cannot create GoodValueObjectWithAccessIssue with a null GoodValueObjectA.");
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		this.valueObject = valueObject;
	}

	private void setValue(String value) {
		if (value == null) {
			try {
				throw new Exception("Cannot create GoodValueObjectWithAccessIssue with a null String.");
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		this.value = value;
	}

	public GoodValueObjectA getValueObject() {
		return this.valueObject;
	}

	private void accessFields() {
		this.value.getClass();
	}

	public void accessibleAccessFields() {
		accessFields();
	}

	@Override
	public boolean equals(Object other) {
		if (! (other instanceof GoodValueObjectWithAccessIssue)) return false;
		return (this.valueObject.equals(((GoodValueObjectWithAccessIssue)other).valueObject)
				&& this.value.compareTo(((GoodValueObjectWithAccessIssue)other).value) == 0);
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