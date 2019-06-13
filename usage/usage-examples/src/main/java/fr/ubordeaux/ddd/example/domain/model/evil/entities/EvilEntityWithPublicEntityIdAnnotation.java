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
public class EvilEntityWithPublicEntityIdAnnotation {
	@EntityID
	public GoodValueObjectA valueObjectId;
	private String value;

	public EvilEntityWithPublicEntityIdAnnotation(GoodValueObjectA valueObjectId) {
		this.setValueObjectID(valueObjectId);
	}

	@Setter
	private void setValueObjectID(GoodValueObjectA valueObjectId) {
		if (valueObjectId == null) {
			try {
				throw new Exception("Cannot create EvilEntityWithPublicEntityIdAnnotation with a null GoodValueObjectA as ID.");
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
		if (! (other instanceof EvilEntityWithPublicEntityIdAnnotation)) return false;
		return this.valueObjectId.equals(((EvilEntityWithPublicEntityIdAnnotation)other).valueObjectId);
	}

	@Override
	public int hashCode() {
		return this.valueObjectId.hashCode();
	}

	@Override
	public String toString() {
		return this.valueObjectId.toString();
	}
}