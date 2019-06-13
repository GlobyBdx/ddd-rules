package fr.ubordeaux.ddd.example.domain.model.evil.aggregates;

import fr.ubordeaux.ddd.annotations.fields.EntityID;
import fr.ubordeaux.ddd.annotations.methods.Setter;
import fr.ubordeaux.ddd.annotations.types.Aggregate;

/**
 * Aggregate
 * 
 */

@Aggregate
public class EvilAggregateWithoutEntityAnnotation {
	@EntityID
	private String valueId;
	private String value;

	public EvilAggregateWithoutEntityAnnotation(String valueId) {
		this.setValueID(valueId);
	}

	@Setter
	private void setValueID(String valueId) {
		if (valueId == null) {
			try {
				throw new Exception("Cannot create EvilAggregateWithoutEntityAnnotation with a null String as ID.");
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		this.valueId = valueId;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getValue() {
		return this.value;
	}

	@Override
	public boolean equals(Object other) {
		if (! (other instanceof EvilAggregateWithoutEntityAnnotation)) return false;
		return this.valueId.compareTo(((EvilAggregateWithoutEntityAnnotation)other).valueId) == 0;
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