package fr.ubordeaux.ddd.cargoshippingsystem.domain.model.shipping;

import java.util.Date;
import java.util.Objects;

import fr.ubordeaux.ddd.annotations.types.Entity;
import fr.ubordeaux.ddd.annotations.fields.EntityID;
import fr.ubordeaux.ddd.annotations.fields.Immutable;

/**
 * Entity
 * 
 */

@Entity
public class HandlingEvent {
	@EntityID
	private CargoTrackingID cargoTrackingID;
	@EntityID
	@Immutable
	private Date completionTime;
	@EntityID
	@Immutable
	private EventType eventType;
	private ScheduleID scheduleID;

	public HandlingEvent(CargoTrackingID cargoTrackingID, Date completionTime, EventType eventType) {
		this.setCargoTrackingID(cargoTrackingID);
		this.setCompletionTime(completionTime);
		this.setEventType(eventType);
	}

	private void setCargoTrackingID(CargoTrackingID cargoTrackingID) {
		if (cargoTrackingID == null) throw new HandlingEventException("Cannot create HandlingEvent with a null CargoTrackingID.");
		this.cargoTrackingID = cargoTrackingID;
	}

	private void setCompletionTime(Date completionTime) {
		if (completionTime == null) throw new HandlingEventException("Cannot create HandlingEvent with a null CompletionTime.");
		this.completionTime = completionTime;
	}

	private void setEventType(EventType eventType) {
		if (eventType == null) throw new HandlingEventException("Cannot create HandlingEvent with a null EventType.");
		this.eventType = eventType;
	}

	public void setScheduleID(ScheduleID scheduleID) {
		this.scheduleID = scheduleID;
	}

	public CargoTrackingID getCargoTrackingID() {
		return this.cargoTrackingID;
	}

	public Date getCompletionTime() {
		return this.completionTime;
	}

	public EventType getEventType() {
		return this.eventType;
	}

	public ScheduleID getScheduleID() {
		return this.scheduleID;
	}

	@Override
	public boolean equals(Object other) {
		if (! (other instanceof HandlingEvent)) return false;
		return (this.cargoTrackingID.equals(((HandlingEvent)other).cargoTrackingID)
				&& this.completionTime.equals(((HandlingEvent)other).completionTime)
				&& this.eventType.equals(((HandlingEvent)other).eventType));
	}

	@Override
	public int hashCode() {
		return Objects.hash(this.cargoTrackingID.toString() + ":" + this.completionTime.toString() + ":" + this.eventType.toString());
	}

	@Override
	public String toString() {
		return this.cargoTrackingID.toString() + ":" + this.completionTime.toString() + ":" + this.eventType.toString();
	}
}