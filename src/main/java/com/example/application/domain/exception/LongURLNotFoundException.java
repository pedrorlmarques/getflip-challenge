package com.example.application.domain.exception;

/**
 * Exception thrown when the long URL is not found.
 */
public class LongURLNotFoundException extends RuntimeException {

	public LongURLNotFoundException(String message) {
		super(message);
	}
}
