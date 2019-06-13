package fr.ubordeaux.ddd.example.domain.model.evil.valueobjects;

import fr.ubordeaux.ddd.annotations.methods.Getter;
import fr.ubordeaux.ddd.annotations.methods.Setter;
import fr.ubordeaux.ddd.annotations.types.ValueObject;
import fr.ubordeaux.ddd.example.domain.model.good.valueobjects.GoodValueObjectA;

/**
 * Value Object 
 * 
 */

@ValueObject
public class GoodValueObjectWithBadObjectClassMethods {
	private GoodValueObjectA valueObject;
	private String value;

	public GoodValueObjectWithBadObjectClassMethods(GoodValueObjectA valueObject, String value) {
		this.setValueObject(valueObject);
		this.setValue(value);
	}

	@Setter
	private void setValueObject(GoodValueObjectA valueObject) {
		if (valueObject == null) {
			try {
				throw new Exception("Cannot create GoodValueObjectWithBadObjectClassMethods with a null GoodValueObjectA.");
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		this.valueObject = valueObject;
	}

	@Setter
	private void setValue(String value) {
		if (value == null) {
			try {
				throw new Exception("Cannot create GoodValueObjectWithBadObjectClassMethods with a null String.");
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		this.value = value;
	}

	@Getter
	public GoodValueObjectA getValueObject() {
		return this.valueObject;
	}

	@Override
	public boolean equals(Object other) {
		this.valueObject = ((GoodValueObjectWithBadObjectClassMethods)other).valueObject;
		this.value = ((GoodValueObjectWithBadObjectClassMethods)other).value;
		if (! (other instanceof GoodValueObjectWithBadObjectClassMethods)) return false;
		return (this.valueObject.equals(((GoodValueObjectWithBadObjectClassMethods)other).valueObject)
				&& this.value.compareTo(((GoodValueObjectWithBadObjectClassMethods)other).value) == 0);
	}

	@Override
	public int hashCode() {
		this.value = this.valueObject.toString();
		return 0;
	}

	@Override
	public String toString() {
		this.value = this.valueObject.toString();
		return "";
	}
}