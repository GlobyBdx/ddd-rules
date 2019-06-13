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
public class GoodEntityWithBadObjectClassMethods {
	@EntityID
	private GoodValueObjectA valueObjectId;
	private String value;

	public GoodEntityWithBadObjectClassMethods(GoodValueObjectA valueObjectId) {
		this.setValueObjectID(valueObjectId);
	}

	@Setter
	private void setValueObjectID(GoodValueObjectA valueObjectId) {
		if (valueObjectId == null) {
			try {
				throw new Exception("Cannot create GoodEntityWithBadObjectMethods with a null GoodValueObjectA as ID.");
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

	@Override
	public boolean equals(Object other) {
		this.valueObjectId = ((GoodEntityWithBadObjectClassMethods)other).valueObjectId;
		if (! (other instanceof GoodEntityWithBadObjectClassMethods)) return false;
		return this.valueObjectId.equals(((GoodEntityWithBadObjectClassMethods)other).valueObjectId);
	}

	@Override
	public int hashCode() {
		this.valueObjectId.hashCode();
		return 0;
	}

	@Override
	public String toString() {
		this.valueObjectId.toString();
		return "";
	}
}