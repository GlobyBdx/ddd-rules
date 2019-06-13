package fr.ubordeaux.ddd.cargoshippingsystem.domain.model.shipping;

import fr.ubordeaux.ddd.cargoshippingsystem.domain.model.location.LocationID;
import fr.ubordeaux.ddd.annotations.types.Factory;

/**
 * Factory
 * 
 */

@Factory
public class CarrierMovementFactory {
	private final CarrierMovementRepository carrierMovementRepository;

	public CarrierMovementFactory(final CarrierMovementRepository carrierMovementRepository) {
		this.carrierMovementRepository = carrierMovementRepository;
	}

	public CarrierMovement newCarrierMovement() {
		CarrierMovement carrierMovement = new CarrierMovement(this.carrierMovementRepository.generateNextScheduleID());
		this.carrierMovementRepository.store(carrierMovement);
		return carrierMovement;
	}

	public CarrierMovement newCarrierMovement(LocationID fromLocationID, LocationID toLocationID) {
		CarrierMovement carrierMovement = this.newCarrierMovement();
		carrierMovement.setFromLocationID(fromLocationID);
		carrierMovement.setToLocationID(toLocationID);
		return carrierMovement;
	}
}