package fr.ubordeaux.ddd.cargoshippingsystem.domain.model.shipping;

/**
 * Exception
 * 
 */

public class CargoException extends RuntimeException {
	private static final long serialVersionUID = 1L;

	public CargoException(String message) {
		super(message);
	}
}