package fr.ubordeaux.ddd.cargoshippingsystem.domain.model.billing;

import java.util.Objects;

import fr.ubordeaux.ddd.annotations.types.ValueObject;

/**
 * Value Object 
 * 
 */

@ValueObject
public class Money {
	private Price price;
	private Currency currency;

	public Money(Price price, Currency currency) {
		this.setPrice(price);
		this.setCurrency(currency);
	}

	private void setPrice(Price customerID)  {
		if (price == null) throw new InvoiceException("Cannot create Money with a null Price.");
		this.price = customerID;
	}

	private void setCurrency(Currency customerRole)  {
		if (currency == null) throw new InvoiceException("Cannot create Money with a null Currency.");
		this.currency = customerRole;
	}

	public Price getPrice()  {
		return this.price;
	}

	public Currency getCurrency()  {
		return this.currency;
	}

	@Override
	public boolean equals(Object other) {
		if (! (other instanceof Money)) return false;
		return (this.price.equals(((Money)other).price)
				&& this.currency.equals(((Money)other).currency));
	}

	@Override
	public int hashCode() {
		return Objects.hash(this.price.toString() + ":" + this.currency.toString());
	}

	@Override
	public String toString() {
		return this.price.toString() + ":" + this.currency.toString();
	}
}