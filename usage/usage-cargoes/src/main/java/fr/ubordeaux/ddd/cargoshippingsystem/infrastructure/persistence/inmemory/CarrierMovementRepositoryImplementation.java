package fr.ubordeaux.ddd.cargoshippingsystem.infrastructure.persistence.inmemory;

import java.util.HashSet;
import java.util.Set;

import fr.ubordeaux.ddd.cargoshippingsystem.domain.model.location.LocationID;
import fr.ubordeaux.ddd.cargoshippingsystem.domain.model.shipping.CarrierMovement;
import fr.ubordeaux.ddd.cargoshippingsystem.domain.model.shipping.CarrierMovementRepository;
import fr.ubordeaux.ddd.cargoshippingsystem.domain.model.shipping.ScheduleID;
import fr.ubordeaux.ddd.annotations.types.Repository;

/**
 * Repository implementation
 * 
 */

@Repository
public class CarrierMovementRepositoryImplementation implements CarrierMovementRepository {
	private Set<CarrierMovement> carrierMovements;

	public CarrierMovementRepositoryImplementation() {
		this.carrierMovements = new HashSet<>();
	}

	@Override
	public void store(CarrierMovement carrierMovement) {
		if (this.findAll().contains(carrierMovement)) {
			this.carrierMovements.remove(carrierMovement);
		}
		this.carrierMovements.add(carrierMovement);
	}

	@Override
	public Set<CarrierMovement> findAll() {
		return this.carrierMovements;
	}

	@Override
	public CarrierMovement findByScheduleID(ScheduleID scheduleID) {
		for (CarrierMovement carrierMovement : this.findAll()) {
			if (carrierMovement.getScheduleID().equals(scheduleID)) {
				return carrierMovement;
			}
		}
		return null;
	}

	@Override
	public Set<CarrierMovement> findByFromToLocationID(LocationID fromLocationID, LocationID toLocationID) {
		Set<CarrierMovement> carrierMovements = new HashSet<>();
		for (CarrierMovement carrierMovement : this.findAll()) {
			if (carrierMovement.getFromLocationID().equals(fromLocationID)
					&& carrierMovement.getToLocationID().equals(toLocationID)) {
				carrierMovements.add(carrierMovement);
			}
		}
		return carrierMovements;
	}

	@Override
	public ScheduleID generateNextScheduleID() {
		// TODO Auto-generated method stub
		return null;
	}
}