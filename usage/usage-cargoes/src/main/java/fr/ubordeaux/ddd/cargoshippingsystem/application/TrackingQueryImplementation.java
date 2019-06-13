package fr.ubordeaux.ddd.cargoshippingsystem.application;

import fr.ubordeaux.ddd.cargoshippingsystem.domain.model.shipping.Cargo;
import fr.ubordeaux.ddd.cargoshippingsystem.domain.model.shipping.CargoException;
import fr.ubordeaux.ddd.cargoshippingsystem.domain.model.shipping.CargoRepository;
import fr.ubordeaux.ddd.cargoshippingsystem.domain.model.shipping.CargoTrackingID;
import fr.ubordeaux.ddd.cargoshippingsystem.domain.model.shipping.HandlingEvent;
import fr.ubordeaux.ddd.annotations.types.Service;

/**
 * Service implementation
 * 
 */

@Service
public class TrackingQueryImplementation implements TrackingQuery {
	private final CargoRepository cargoRepository;

	public TrackingQueryImplementation(CargoRepository cargoRepository) {
		this.cargoRepository = cargoRepository;
	}

	@Override
	public HandlingEvent inspectCargo(CargoTrackingID cargoTrackingID) {
		Cargo cargo = this.cargoRepository.findByCargoTrackingID(cargoTrackingID);
		if (cargo == null) {
			throw new CargoException("Cargo with given ID not found.");
		}
		return cargo.getDeliveryHistory().getLastHandlingEvent();
	}
}