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
public class EvilValueObjectWithPublicSetters {
	private GoodValueObjectA valueObject;
	private String value;

	public EvilValueObjectWithPublicSetters(GoodValueObjectA valueObject, String value) {
		this.setValueObject(valueObject);
		this.setValue(value);
	}

	@Setter
	public void setValueObject(GoodValueObjectA valueObject) {
		if (valueObject == null) {
			try {
				throw new Exception("Cannot create EvilValueObjectWithPublicSetters with a null GoodValueObjectA.");
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		this.valueObject = valueObject;
	}

	@Setter
	public void setValue(String value) {
		if (value == null) {
			try {
				throw new Exception("Cannot create EvilValueObjectWithPublicSetters with a null String.");
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
		if (! (other instanceof EvilValueObjectWithPublicSetters)) return false;
		return (this.valueObject.equals(((EvilValueObjectWithPublicSetters)other).valueObject)
				&& this.value.compareTo(((EvilValueObjectWithPublicSetters)other).value) == 0);
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