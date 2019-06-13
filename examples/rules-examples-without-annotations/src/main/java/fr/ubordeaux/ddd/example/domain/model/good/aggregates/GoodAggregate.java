package fr.ubordeaux.ddd.example.domain.model.good.aggregates;

import fr.ubordeaux.ddd.example.domain.model.good.entities.GoodEntityB;
import fr.ubordeaux.ddd.example.domain.model.good.entities.GoodEntityC;

/**
 * Aggregate
 * 
 */

public class GoodAggregate {
	private GoodEntityB entityId;
	private GoodEntityC entity;
	private String value;

	public GoodAggregate(GoodEntityB entityId) {
		this.setEntityID(entityId);
	}

	private void setEntityID(GoodEntityB entityId) {
		if (entityId == null) {
			try {
				throw new Exception("Cannot create GoodAggregate with a null GoodEntityB as ID.");
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		this.entityId = entityId;
	}

	public void setEntity(GoodEntityC entity) {
		this.entity = entity;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public GoodEntityB getEntityID() {
		return this.entityId;
	}

	public GoodEntityC getEntity() {
		return this.entity;
	}

	public String getValue() {
		return this.value;
	}

	@Override
	public boolean equals(Object other) {
		if (! (other instanceof GoodAggregate)) return false;
		return this.entityId.equals(((GoodAggregate)other).entityId);
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