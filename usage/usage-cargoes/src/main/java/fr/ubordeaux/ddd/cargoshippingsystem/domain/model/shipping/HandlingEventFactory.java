package fr.ubordeaux.ddd.cargoshippingsystem.domain.model.shipping;

import java.util.Date;

import fr.ubordeaux.ddd.annotations.types.Factory;

/**
 * Factory
 * 
 */

@Factory
public class HandlingEventFactory {
	private final HandlingEventRepository handlingEventRepository;
	private static final Date LONG_TIME = new Date(Long.MAX_VALUE);

	public HandlingEventFactory(final HandlingEventRepository handlingEventRepository) {
		this.handlingEventRepository = handlingEventRepository;
	}

	public HandlingEvent newHandlingEvent(CargoTrackingID cargoTrackingID, Date completionTime, EventType eventType) {
		HandlingEvent handlingEvent = new HandlingEvent(cargoTrackingID, completionTime, eventType);
		this.handlingEventRepository.store(handlingEvent);
		return handlingEvent;
	}

	public HandlingEvent newBookingHandlingEvent(CargoTrackingID cargoTrackingID, Date completionTime) {
		HandlingEvent handlingEvent = this.newHandlingEvent(cargoTrackingID, LONG_TIME, EventType.BOOKED);
		return handlingEvent;
	}

	public HandlingEvent newSendingHandlingEvent(CargoTrackingID cargoTrackingID, Date completionTime, ScheduleID scheduleID) {
		HandlingEvent handlingEvent = this.newHandlingEvent(cargoTrackingID, LONG_TIME, EventType.SENT);
		handlingEvent.setScheduleID(scheduleID);
		return handlingEvent;
	}

	public HandlingEvent newReceivingHandlingEvent(CargoTrackingID cargoTrackingID, Date completionTime) {
		HandlingEvent handlingEvent = this.newHandlingEvent(cargoTrackingID, LONG_TIME, EventType.RECEIVED);
		return handlingEvent;
	}

	public HandlingEvent newCancelingHandlingEvent(CargoTrackingID cargoTrackingID, Date completionTime) {
		HandlingEvent handlingEvent = this.newHandlingEvent(cargoTrackingID, LONG_TIME, EventType.CANCELED);
		return handlingEvent;
	}
}