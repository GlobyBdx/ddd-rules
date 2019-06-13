package fr.ubordeaux.ddd.example.domain.model.evil.entities;

/**
 * Entity
 * 
 */

public class GoodEntityWithAccessIssue {
	private String valueId;
	private String value;

	public GoodEntityWithAccessIssue(String valueId) {
		this.setValueID(valueId);
	}

	private void setValueID(String valueId) {
		if (valueId == null) {
			try {
				throw new Exception("Cannot create GoodEntityWithAccessIssue with a null String as ID.");
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

	private void accessFields() {
		this.valueId.getClass();
	}

	public void accessibleAccessFields() {
		accessFields();
	}

	@Override
	public boolean equals(Object other) {
		if (! (other instanceof GoodEntityWithAccessIssue)) return false;
		return this.valueId.compareTo(((GoodEntityWithAccessIssue)other).valueId) == 0;
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