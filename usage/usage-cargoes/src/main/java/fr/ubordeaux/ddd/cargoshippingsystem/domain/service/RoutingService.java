package fr.ubordeaux.ddd.cargoshippingsystem.domain.service;

import java.util.List;

import fr.ubordeaux.ddd.cargoshippingsystem.domain.model.location.Location;
import fr.ubordeaux.ddd.cargoshippingsystem.domain.model.shipping.DeliverySpecification;

/**
 * Service interface
 *
 */

public interface RoutingService {
	/**
	 * @param DeliverySpecification deliverySpecification
	 * @return A list of locations that satisfy the delivery specification. May be an empty list if no route is found.
	 */
	List<Location> fetchRoutesForDeliverySpecification(DeliverySpecification deliverySpecification);
}