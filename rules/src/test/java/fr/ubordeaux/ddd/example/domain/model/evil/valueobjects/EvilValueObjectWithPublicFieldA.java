package fr.ubordeaux.ddd.example.domain.model.evil.valueobjects;

import java.util.Objects;

import fr.ubordeaux.ddd.annotations.methods.Getter;
import fr.ubordeaux.ddd.annotations.methods.Setter;
import fr.ubordeaux.ddd.annotations.types.ValueObject;
import fr.ubordeaux.ddd.example.domain.model.good.valueobjects.GoodValueObjectA;

/**
 * Value Object 
 * 
 */

@ValueObject
public class EvilValueObjectWithPublicFieldA {
	public GoodValueObjectA valueObject;
	private String value;

	public EvilValueObjectWithPublicFieldA(GoodValueObjectA valueObject, String value) {
		this.setValueObject(valueObject);
		this.setValue(value);
	}

	@Setter
	private void setValueObject(GoodValueObjectA valueObject) {
		if (valueObject == null) {
			try {
				throw new Exception("Cannot create EvilValueObjectWithPublicFieldA with a null GoodValueObjectA.");
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
				throw new Exception("Cannot create EvilValueObjectWithPublicFieldA with a null String.");
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
		if (! (other instanceof EvilValueObjectWithPublicFieldA)) return false;
		return (this.valueObject.equals(((EvilValueObjectWithPublicFieldA)other).valueObject)
				&& this.value.compareTo(((EvilValueObjectWithPublicFieldA)other).value) == 0);
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