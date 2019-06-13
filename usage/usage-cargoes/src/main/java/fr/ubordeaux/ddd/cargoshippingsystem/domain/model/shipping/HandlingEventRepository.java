package fr.ubordeaux.ddd.cargoshippingsystem.domain.model.shipping;

import java.util.Date;
import java.util.Set;

/**
 * Repository interface
 * 
 */

public interface HandlingEventRepository {
	public void store(HandlingEvent handlingEvent);
	public Set<HandlingEvent> findAll();
	public Set<HandlingEvent> findByCargoTrackingID(CargoTrackingID cargoTrackingID);
	public Set<HandlingEvent> findByCargoTrackingIDType(CargoTrackingID cargoTrackingID, EventType type);
	public HandlingEvent findMostRecentByCargoTrackingIDType(CargoTrackingID cargoTrackingID, EventType type);
	public HandlingEvent findByCargoTrackingIDCompletionTimeType(CargoTrackingID cargoTrackingID, Date completionTime, EventType type);
	public HandlingEvent findByScheduleID(ScheduleID scheduleID);
}