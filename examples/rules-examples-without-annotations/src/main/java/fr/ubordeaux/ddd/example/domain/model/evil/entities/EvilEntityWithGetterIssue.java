package fr.ubordeaux.ddd.example.domain.model.evil.entities;

/**
 * Entity
 * 
 */

public class EvilEntityWithGetterIssue {
	private String valueId;
	private String value;

	public EvilEntityWithGetterIssue(String valueId) {
		this.setValueID(valueId);
	}

	private void setValueID(String valueId) {
		if (valueId == null) {
			try {
				throw new Exception("Cannot create EvilEntityWithGetterIssue with a null String as ID.");
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		this.valueId = valueId;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getValueID() {
		return this.valueId;
	}

	public String getValue() {
		return this.value;
	}

	@Override
	public boolean equals(Object other) {
		if (! (other instanceof EvilEntityWithGetterIssue)) return false;
		return this.valueId.compareTo(((EvilEntityWithGetterIssue)other).valueId) == 0;
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