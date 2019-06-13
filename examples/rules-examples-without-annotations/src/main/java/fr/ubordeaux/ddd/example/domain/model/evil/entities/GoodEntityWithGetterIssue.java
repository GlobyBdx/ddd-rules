package fr.ubordeaux.ddd.example.domain.model.evil.entities;

/**
 * Entity
 * 
 */

public class GoodEntityWithGetterIssue {
	private String valueId;
	private String value;

	public GoodEntityWithGetterIssue(String valueId) {
		this.setValueID(valueId);
	}

	private void setValueID(String valueId) {
		if (valueId == null) {
			try {
				throw new Exception("Cannot create GoodEntityWithGetterIssue with a null String as ID.");
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		this.valueId = valueId;
	}

	public void setValue(String value) {
		this.value = value;
	}

	private String getValueID() {
		return this.valueId;
	}

	public String accessibleGetValueID() {
		return getValueID();
	}

	public String getValue() {
		return this.value;
	}

	@Override
	public boolean equals(Object other) {
		if (! (other instanceof GoodEntityWithGetterIssue)) return false;
		return this.valueId.compareTo(((GoodEntityWithGetterIssue)other).valueId) == 0;
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