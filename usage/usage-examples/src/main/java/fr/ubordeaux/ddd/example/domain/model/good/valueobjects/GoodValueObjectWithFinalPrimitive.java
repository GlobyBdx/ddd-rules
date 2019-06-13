package fr.ubordeaux.ddd.example.domain.model.good.valueobjects;

import fr.ubordeaux.ddd.annotations.methods.Getter;
import fr.ubordeaux.ddd.annotations.types.ValueObject;

/**
 * Value Object 
 * 
 */

@ValueObject
public class GoodValueObjectWithFinalPrimitive {
	final private long longValue;
	final private byte byteValue;

	public GoodValueObjectWithFinalPrimitive(long longValue, byte byteValue) {
		this.longValue = longValue;
		this.byteValue = byteValue;
	}

	@Getter
	public long getLongValue() {
		return this.longValue;
	}

	@Getter
	public long getByteValue() {
		return this.byteValue;
	}

	public void accessFields() {
		System.out.println(this.longValue);
		System.out.println(this.byteValue);
	}

	@Override
	public boolean equals(Object other) {
		if (! (other instanceof GoodValueObjectWithFinalPrimitive)) return false;
		return (this.longValue == ((GoodValueObjectWithFinalPrimitive)other).longValue
				&& this.byteValue == ((GoodValueObjectWithFinalPrimitive)other).byteValue);
	}

	@Override
	public int hashCode() {
		return Long.hashCode(this.longValue) + Byte.hashCode(byteValue);
	}

	@Override
	public String toString() {
		return Long.toString(this.longValue) + Byte.toString(byteValue);
	}
}