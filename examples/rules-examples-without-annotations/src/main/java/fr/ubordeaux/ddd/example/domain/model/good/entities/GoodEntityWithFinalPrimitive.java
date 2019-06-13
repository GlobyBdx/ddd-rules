package fr.ubordeaux.ddd.example.domain.model.good.entities;

/**
 * Entity
 * 
 */

public class GoodEntityWithFinalPrimitive {
	final private long longId;
	final private byte byteId;
	private String value;

	public GoodEntityWithFinalPrimitive(long longId, byte byteId) {
		this.longId = longId;
		this.byteId = byteId;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public long getLongID() {
		return this.longId;
	}

	public long getByteID() {
		return this.byteId;
	}

	public String getValue() {
		return this.value;
	}

	public void accessFields() {
		System.out.println(this.longId);
		System.out.println(this.byteId);
	}

	@Override
	public boolean equals(Object other) {
		if (! (other instanceof GoodEntityWithFinalPrimitive)) return false;
		return (this.longId == ((GoodEntityWithFinalPrimitive)other).longId
				&& this.byteId == ((GoodEntityWithFinalPrimitive)other).byteId);
	}

	@Override
	public int hashCode() {
		return Long.hashCode(this.longId) + Byte.hashCode(this.byteId);
	}

	@Override
	public String toString() {
		return Long.toString(this.longId) + Byte.toString(this.byteId);
	}
}