package fr.ubordeaux.ddd.cargoshippingsystem.domain.model.shipping;

/**
 * Exception
 * 
 */

public class CarrierMovementException extends RuntimeException {
	private static final long serialVersionUID = 1L;

	public CarrierMovementException(String message) {
		super(message);
	}
}