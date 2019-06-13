package fr.ubordeaux.ddd.cargoshippingsystem.application;

/**
 * Exception
 * 
 */

public class IncidentLoggingException extends RuntimeException {
	private static final long serialVersionUID = 1L;

	public IncidentLoggingException(String message) {
		super(message);
	}
}