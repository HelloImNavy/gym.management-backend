package com.gym.gym.management.exceptions;

public class GymException extends RuntimeException {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	// Constructor que acepta solo el mensaje de error
    public GymException(String message) {
        super(message);
    }

    // Constructor que acepta el mensaje y la causa del error
    public GymException(String message, Throwable cause) {
        super(message, cause);
    }
}
