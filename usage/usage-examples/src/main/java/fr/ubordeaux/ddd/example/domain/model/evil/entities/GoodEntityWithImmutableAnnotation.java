package fr.ubordeaux.ddd.example.domain.model.evil.entities;

import fr.ubordeaux.ddd.annotations.fields.EntityID;
import fr.ubordeaux.ddd.annotations.fields.Immutable;
import fr.ubordeaux.ddd.annotations.methods.Getter;
import fr.ubordeaux.ddd.annotations.methods.Setter;
import fr.ubordeaux.ddd.annotations.types.Entity;

/**
 * Entity
 * 
 */

@Entity
public class GoodEntityWithImmutableAnnotation {
	@EntityID
	@Immutable
	private String valueId;
	private String value;

	public GoodEntityWithImmutableAnnotation(String valueId) {
		this.setValueID(valueId);
	}

	@Setter
	private void setValueID(String valueId) {
		if (valueId == null) {
			try {
				throw new Exception("Cannot create GoodEntityWithImmutableAnnotation with a null String as ID.");
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		this.valueId = valueId;
	}

	public void setValue(String value) {
		this.value = value;
	}

	@Getter
	public String getValueID() {
		return this.valueId;
	}

	public String getValue() {
		return this.value;
	}

	public void accessFields() {
		this.valueId.getClass();
	}

	@Override
	public boolean equals(Object other) {
		if (! (other instanceof GoodEntityWithImmutableAnnotation)) return false;
		return this.valueId.compareTo(((GoodEntityWithImmutableAnnotation)other).valueId) == 0;
	}

	@Override
	public int hashCode() {
		return this.valueId.hashCode();
	}

	@Override
	public String toString() {
		return this.valueId;
	}
}