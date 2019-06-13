package fr.ubordeaux.ddd.cargoshippingsystem.domain.model.shipping;

import java.util.Set;

import fr.ubordeaux.ddd.cargoshippingsystem.domain.model.location.LocationID;

/**
 * Repository interface
 * 
 */

public interface CarrierMovementRepository {
	public void store(CarrierMovement carrierMovement);
	public Set<CarrierMovement> findAll();
	public CarrierMovement findByScheduleID(ScheduleID scheduleID);
	public Set<CarrierMovement> findByFromToLocationID(LocationID fromLocationID, LocationID toLocationID);
	public ScheduleID generateNextScheduleID();
}