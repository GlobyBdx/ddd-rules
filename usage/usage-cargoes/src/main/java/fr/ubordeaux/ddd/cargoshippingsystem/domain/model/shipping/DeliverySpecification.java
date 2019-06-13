package fr.ubordeaux.ddd.cargoshippingsystem.domain.model.shipping;

import java.util.Objects;

import fr.ubordeaux.ddd.cargoshippingsystem.domain.model.location.LocationID;
import fr.ubordeaux.ddd.annotations.types.ValueObject;
import fr.ubordeaux.ddd.annotations.fields.Immutable;

/**
 * Value Object
 * 
 */

@ValueObject
public class DeliverySpecification {
	private LocationID destinationID;
	@Immutable
	private EventType deliveryStatus;

	public DeliverySpecification(LocationID destinationID, EventType deliveryStatus) {
		this.setDestinationID(destinationID);
		this.setDeliveryStatus(deliveryStatus);
	}

	private void setDestinationID(LocationID destinationID) {
		if (destinationID == null) throw new CargoException("Cannot create DeliverySpecification without LocationID.");
		this.destinationID = destinationID;
	}

	private void setDeliveryStatus(EventType deliveryStatus) {
		if (deliveryStatus == null) throw new CargoException("Cannot create DeliverySpecification without DeliveryStatus.");
		this.deliveryStatus = deliveryStatus;
	}

	public LocationID getDestinationID() {
		return this.destinationID;
	}

	public EventType getDeliveryStatus() {
		return this.deliveryStatus;
	}

	@Override
	public boolean equals(Object other) {
		if (! (other instanceof DeliverySpecification)) return false;
		return (this.destinationID.equals(((DeliverySpecification) other).destinationID)
				&& this.deliveryStatus.equals(((DeliverySpecification) other).deliveryStatus));
	}

	@Override
	public int hashCode() {
		return Objects.hash(this.destinationID.toString() + ":" + this.deliveryStatus.toString());
	}

	@Override
	public String toString() {
		return this.destinationID.toString() + ":" + this.deliveryStatus.toString();
	}
}