package fr.ubordeaux.ddd.cargoshippingsystem.infrastructure.persistence.inmemory;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import fr.ubordeaux.ddd.cargoshippingsystem.domain.model.shipping.CargoTrackingID;
import fr.ubordeaux.ddd.cargoshippingsystem.domain.model.shipping.EventType;
import fr.ubordeaux.ddd.cargoshippingsystem.domain.model.shipping.HandlingEvent;
import fr.ubordeaux.ddd.cargoshippingsystem.domain.model.shipping.HandlingEventRepository;
import fr.ubordeaux.ddd.cargoshippingsystem.domain.model.shipping.ScheduleID;
import fr.ubordeaux.ddd.annotations.types.Repository;

/**
 * Repository implementation
 * 
 */

@Repository
public class HandlingEventRepositoryImplementation implements HandlingEventRepository {
	private Set<HandlingEvent> handlingEvents;

	public HandlingEventRepositoryImplementation() {
		this.handlingEvents = new HashSet<>();
	}

	@Override
	public void store(HandlingEvent handlingEvent) {
		if (this.findAll().contains(handlingEvent)) {
			this.handlingEvents.remove(handlingEvent);
		}
		this.handlingEvents.add(handlingEvent);
	}

	@Override
	public Set<HandlingEvent> findAll() {
		return this.handlingEvents;
	}

	@Override
	public Set<HandlingEvent> findByCargoTrackingID(CargoTrackingID cargoTrackingID) {
		Set<HandlingEvent> handlingEvents = new HashSet<>();
		for (HandlingEvent handlingEvent : this.findAll()) {
			if (handlingEvent.getCargoTrackingID().equals(cargoTrackingID)) {
				handlingEvents.add(handlingEvent);
			}
		}
		return handlingEvents;
	}

	@Override
	public Set<HandlingEvent> findByCargoTrackingIDType(CargoTrackingID cargoTrackingID, EventType eventType) {
		Set<HandlingEvent> handlingEvents = new HashSet<>();
		for (HandlingEvent handlingEvent : this.findAll()) {
			if (handlingEvent.getCargoTrackingID().equals(cargoTrackingID)
					&& handlingEvent.getEventType().equals(eventType)) {
				handlingEvents.add(handlingEvent);
			}
		}
		return handlingEvents;
	}

	@Override
	public HandlingEvent findMostRecentByCargoTrackingIDType(CargoTrackingID cargoTrackingID, EventType type) {
		HandlingEvent event = null;
		for (HandlingEvent handlingEvent : this.findAll()) {
			if (event == null || handlingEvent.getCompletionTime().after(event.getCompletionTime())) {
				event = handlingEvent;
			}
		}
		return event;
	}

	@Override
	public HandlingEvent findByCargoTrackingIDCompletionTimeType(CargoTrackingID cargoTrackingID, Date completionTime, EventType eventType) {
		for (HandlingEvent handlingEvent : this.findAll()) {
			if (handlingEvent.getCargoTrackingID().equals(cargoTrackingID)
					&& handlingEvent.getCompletionTime().equals(completionTime)
					&& handlingEvent.getEventType().equals(eventType)) {
				return handlingEvent;
			}
		}
		return null;
	}

	@Override
	public HandlingEvent findByScheduleID(ScheduleID scheduleID) {
		for (HandlingEvent handlingEvent : this.findAll()) {
			if (handlingEvent.getScheduleID().equals(scheduleID)) {
				return handlingEvent;
			}
		}
		return null;
	}
}