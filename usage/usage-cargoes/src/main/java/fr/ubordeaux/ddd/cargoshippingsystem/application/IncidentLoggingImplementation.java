package fr.ubordeaux.ddd.cargoshippingsystem.application;

import fr.ubordeaux.ddd.cargoshippingsystem.domain.model.location.Location;
import fr.ubordeaux.ddd.cargoshippingsystem.domain.model.location.LocationException;
import fr.ubordeaux.ddd.cargoshippingsystem.domain.model.location.LocationID;
import fr.ubordeaux.ddd.cargoshippingsystem.domain.model.location.LocationRepository;
import fr.ubordeaux.ddd.cargoshippingsystem.domain.model.shipping.Cargo;
import fr.ubordeaux.ddd.cargoshippingsystem.domain.model.shipping.CargoException;
import fr.ubordeaux.ddd.cargoshippingsystem.domain.model.shipping.CargoRepository;
import fr.ubordeaux.ddd.cargoshippingsystem.domain.model.shipping.CargoTrackingID;
import fr.ubordeaux.ddd.cargoshippingsystem.domain.model.shipping.CarrierMovement;
import fr.ubordeaux.ddd.cargoshippingsystem.domain.model.shipping.CarrierMovementFactory;
import fr.ubordeaux.ddd.cargoshippingsystem.domain.model.shipping.CarrierMovementRepository;
import fr.ubordeaux.ddd.cargoshippingsystem.domain.model.shipping.HandlingEvent;
import fr.ubordeaux.ddd.cargoshippingsystem.domain.model.shipping.HandlingEventFactory;
import fr.ubordeaux.ddd.cargoshippingsystem.domain.model.shipping.HandlingEventRepository;
import fr.ubordeaux.ddd.cargoshippingsystem.domain.model.shipping.ScheduleID;
import fr.ubordeaux.ddd.annotations.types.Service;

/**
 * Service implementation
 * 
 */

@Service
public class IncidentLoggingImplementation implements IncidentLogging {
	private final CargoRepository cargoRepository;
	private final CarrierMovementRepository carrierMovementRepository;
	private final LocationRepository locationRepository;

	private final CarrierMovementFactory carrierMovementFactory;
	private final HandlingEventFactory handlingEventFactory;

	public IncidentLoggingImplementation(CargoRepository cargoRepository, LocationRepository locationRepository, CarrierMovementRepository carrierMovementRepository, HandlingEventRepository handlingEventRepository) {
		this.cargoRepository = cargoRepository;
		this.locationRepository = locationRepository;
		this.carrierMovementRepository = carrierMovementRepository;
		this.carrierMovementFactory = new CarrierMovementFactory(carrierMovementRepository);
		this.handlingEventFactory = new HandlingEventFactory(handlingEventRepository);
	}

	private Cargo checkCargo(CargoTrackingID cargoTrackingID) {
		Cargo cargo = this.cargoRepository.findByCargoTrackingID(cargoTrackingID);
		if (cargo == null) {
			throw new CargoException("Cargo with given ID not found.");
		}
		if (cargo.getDeliverySpecification() == null) {
			throw new BookingException("Cargo with given ID not booked.");
		}
		return cargo;
	}

	private Location checkLocation(LocationID locationID) {
		Location location = this.locationRepository.findByLocationID(locationID);
		if (location == null) {
			throw new LocationException("Location with given ID not found.");
		}
		return location;
	}

	@Override
	public void cargoHasBeenSent(CargoTrackingID cargoTrackingID, LocationID fromLocationID, LocationID toLocationID) {
		Cargo cargo = this.checkCargo(cargoTrackingID);
		this.checkLocation(fromLocationID);
		this.checkLocation(toLocationID);
		CarrierMovement carrierMovement = this.carrierMovementFactory.newCarrierMovement(fromLocationID, toLocationID);
		HandlingEvent handlingEvent = this.handlingEventFactory.newSendingHandlingEvent(cargo.getCargoTrackingID(), null, carrierMovement.getScheduleID());
		cargo.getDeliveryHistory().addHandlingEvent(handlingEvent);
	}

	@Override
	public void cargoHasBeenReceived(CargoTrackingID cargoTrackingID) {
		Cargo cargo = this.checkCargo(cargoTrackingID);
		ScheduleID scheduleID = cargo.getDeliveryHistory().getLastHandlingEvent().getScheduleID();
		if (scheduleID == null) {
			throw new IncidentLoggingException("Cargo with given ID cannot be located.");
		}
		if (this.carrierMovementRepository.findByScheduleID(scheduleID).getToLocationID() != cargo.getDeliverySpecification().getDestinationID()) {
			throw new IncidentLoggingException("Cargo with given ID cannot be received.");
		}
		HandlingEvent handlingEvent = this.handlingEventFactory.newReceivingHandlingEvent(cargo.getCargoTrackingID(), null);
		cargo.getDeliveryHistory().addHandlingEvent(handlingEvent);
	}
}