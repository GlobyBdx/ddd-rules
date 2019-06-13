package fr.ubordeaux.ddd.cargoshippingsystem.domain.model.shipping;

import java.util.Set;

import fr.ubordeaux.ddd.annotations.types.Factory;

/**
 * Factory ("Repeat Business" scenario)
 * 
 */

@Factory
public class CargoFactory {
	private final CargoRepository cargoRepository;

	public CargoFactory(final CargoRepository cargoRepository) {
		this.cargoRepository = cargoRepository;
	}

	public Cargo newCargo() {
		Cargo cargo = new Cargo(this.cargoRepository.generateNextCargoTrackingID());
		this.cargoRepository.store(cargo);
		return cargo;
	}

	public Cargo newCargo(Set<CargoCustomer> cargoCustomers) {
		Cargo cargo = this.newCargo();
		cargo.setCargoCustomers(cargoCustomers);
		return cargo;
	}

	public Cargo newCargo(Cargo prototype) {
		return this.newCargo(prototype.getCargoCustomers());
	}
}