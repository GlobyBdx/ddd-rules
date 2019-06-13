package fr.ubordeaux.ddd.example.evil.aggregates;

/**
 * Aggregate
 * 
 */

public class EvilAggregateOutsideDomainLayer {
	private String valueId;
	private String value;

	public EvilAggregateOutsideDomainLayer(String valueId) {
		this.setValueID(valueId);
	}

	private void setValueID(String valueId) {
		if (valueId == null) {
			try {
				throw new Exception("Cannot create EvilAggregateOutsideDomainLayer with a null String as ID.");
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
		if (! (other instanceof EvilAggregateOutsideDomainLayer)) return false;
		return this.valueId.compareTo(((EvilAggregateOutsideDomainLayer)other).valueId) == 0;
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