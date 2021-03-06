package fr.ubordeaux.ddd.example.domain.model.evil.aggregates;

import fr.ubordeaux.ddd.example.domain.model.good.entities.GoodEntityA;

/**
 * Aggregate
 * 
 */

public class EvilAggregateWithExternallyAccessedInternalObjects {
	private GoodEntityA entityId;
	private String value;

	public EvilAggregateWithExternallyAccessedInternalObjects(GoodEntityA entityId) {
		this.setEntityID(entityId);
	}

	private void setEntityID(GoodEntityA entityId) {
		if (entityId == null) {
			try {
				throw new Exception("Cannot create EvilAggregateWithExternallyAccessedFields with a null GoodEntityA as ID.");
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		this.entityId = entityId;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public GoodEntityA getEntityID() {
		return this.entityId;
	}

	public String getValue() {
		return this.value;
	}

	@Override
	public boolean equals(Object other) {
		if (! (other instanceof EvilAggregateWithExternallyAccessedInternalObjects)) return false;
		return this.entityId.equals(((EvilAggregateWithExternallyAccessedInternalObjects)other).entityId);
	}

	@Override
	public int hashCode() {
		return this.entityId.hashCode();
	}

	@Override
	public String toString() {
		return this.entityId.toString();
	}
}