package fr.ubordeaux.ddd.example.domain.model.evil.entities;

import fr.ubordeaux.ddd.annotations.fields.EntityID;
import fr.ubordeaux.ddd.annotations.methods.Getter;
import fr.ubordeaux.ddd.annotations.methods.Setter;
import fr.ubordeaux.ddd.annotations.types.Entity;
import fr.ubordeaux.ddd.example.domain.model.good.valueobjects.GoodValueObjectA;

/**
 * Entity
 * 
 */

@Entity
public class EvilEntityWithBadObjectClassMethodsA {
	@EntityID
	private GoodValueObjectA valueObjectId;
	private String value;

	public EvilEntityWithBadObjectClassMethodsA(GoodValueObjectA valueObjectId) {
		this.setValueObjectID(valueObjectId);
	}

	@Setter
	private void setValueObjectID(GoodValueObjectA valueObjectId) {
		if (valueObjectId == null) {
			try {
				throw new Exception("Cannot create EvilEntityWithBadObjectClassMethodsA with a null GoodValueObjectA as ID.");
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		this.valueObjectId = valueObjectId;
	}

	public void setValue(String value) {
		this.value = value;
	}

	@Getter
	public GoodValueObjectA getValueObjectID() {
		return this.valueObjectId;
	}

	public String getValue() {
		return this.value;
	}

	public boolean equals(EvilEntityWithBadObjectClassMethodsA other) {
		return this.valueObjectId.equals(other.valueObjectId);
	}

	public int hashCode(int hashCode) {
		return hashCode;
	}

	public String toString(String toString) {
		return toString;
	}
}