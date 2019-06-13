package fr.ubordeaux.ddd.cargoshippingsystem.application;

/**
 * Exception
 * 
 */

public class TrackingQueryException extends RuntimeException {
	private static final long serialVersionUID = 1L;

	public TrackingQueryException(String message) {
		super(message);
	}
}