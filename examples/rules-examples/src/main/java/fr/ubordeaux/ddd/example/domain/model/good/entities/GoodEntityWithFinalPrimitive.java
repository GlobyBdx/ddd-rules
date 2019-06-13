package fr.ubordeaux.ddd.example.domain.model.good.entities;

import fr.ubordeaux.ddd.annotations.fields.EntityID;
import fr.ubordeaux.ddd.annotations.methods.Getter;
import fr.ubordeaux.ddd.annotations.types.Entity;

/**
 * Entity
 * 
 */

@Entity
public class GoodEntityWithFinalPrimitive {
	@EntityID
	final private long longId;
	@EntityID
	final private byte byteId;
	private String value;

	public GoodEntityWithFinalPrimitive(long longId, byte byteId) {
		this.longId = longId;
		this.byteId = byteId;
	}

	public void setValue(String value) {
		this.value = value;
	}

	@Getter
	public long getLongID() {
		return this.longId;
	}

	@Getter
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