package fr.ubordeaux.ddd.cargoshippingsystem.domain.model.shipping;

import java.util.Set;

import fr.ubordeaux.ddd.cargoshippingsystem.domain.model.customer.CustomerID;
import fr.ubordeaux.ddd.cargoshippingsystem.domain.model.customer.CustomerRole;

/**
 * Repository interface (Auto-generated CargoTrackingID must be a new unique ID or an already existing not in use ID)
 * 
 */

public interface CargoRepository {
	public void store(Cargo cargo);
	public Set<Cargo> findAll();
	public Cargo findByCargoTrackingID(CargoTrackingID cargoTrackingID);
	public Set<Cargo> findByCargoCustomer(CargoCustomer cargoCustomer);
	public Set<Cargo> findByCustomerID(CustomerID customerID);
	public Set<Cargo> findByCustomerRole(CustomerRole customerRole);
	public CargoTrackingID generateNextCargoTrackingID();
}