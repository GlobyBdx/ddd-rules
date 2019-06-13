package fr.ubordeaux.ddd.example.domain.model.evil.valueobjects;

import java.util.Objects;

import fr.ubordeaux.ddd.example.domain.model.good.valueobjects.GoodValueObjectA;

/**
 * Value Object 
 * 
 */

public class GoodValueObjectWithGetterIssue {
	private GoodValueObjectA valueObject;
	private String value;

	public GoodValueObjectWithGetterIssue(GoodValueObjectA valueObject, String value) {
		this.setValueObject(valueObject);
		this.setValue(value);
	}

	private void setValueObject(GoodValueObjectA valueObject) {
		if (valueObject == null) {
			try {
				throw new Exception("Cannot create GoodValueObjectWithGetterIssue with a null GoodValueObjectA.");
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		this.valueObject = valueObject;
	}

	private void setValue(String value) {
		if (value == null) {
			try {
				throw new Exception("Cannot create GoodValueObjectWithGetterIssue with a null String.");
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		this.value = value;
	}

	public GoodValueObjectA getValueObject() {
		return this.valueObject;
	}

	private String getValue() {
		return this.value;
	}

	public String accessibleGetValue() {
		return getValue();
	}

	@Override
	public boolean equals(Object other) {
		if (! (other instanceof GoodValueObjectWithGetterIssue)) return false;
		return (this.valueObject.equals(((GoodValueObjectWithGetterIssue)other).valueObject)
				&& this.value.compareTo(((GoodValueObjectWithGetterIssue)other).value) == 0);
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