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
public class EvilValueObjectWithBadObjectClassMethodsB {
	private GoodValueObjectA valueObject;
	@SuppressWarnings("unused")
	private String value;

	public EvilValueObjectWithBadObjectClassMethodsB(GoodValueObjectA valueObject, String value) {
		this.setValueObject(valueObject);
		this.setValue(value);
	}

	@Setter
	private void setValueObject(GoodValueObjectA valueObject) {
		if (valueObject == null) {
			try {
				throw new Exception("Cannot create EvilValueObjectWithBadObjectClassMethodsB with a null GoodValueObjectA.");
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
				throw new Exception("Cannot create EvilValueObjectWithBadObjectClassMethodsB with a null String.");
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
		if (! (other instanceof EvilValueObjectWithBadObjectClassMethodsB)) return false;
		return this.valueObject.equals(((EvilValueObjectWithBadObjectClassMethodsB)other).valueObject);
	}

	@Override
	public int hashCode() {
		return this.valueObject.hashCode();
	}

	@Override
	public String toString() {
		return this.valueObject.toString();
	}
}