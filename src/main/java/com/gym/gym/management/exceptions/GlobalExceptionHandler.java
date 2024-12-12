package com.gym.gym.management.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

@ControllerAdvice
public class GlobalExceptionHandler {

    // Maneja GymException y sus subclases
    @ExceptionHandler(GymException.class)
    public ResponseEntity<String> handleGymException(GymException ex, WebRequest request) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
    }

    // Maneja cualquier otra excepción que no haya sido capturada
    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleGeneralException(Exception ex, WebRequest request) {
        return new ResponseEntity<>("Error interno en el servidor.", HttpStatus.INTERNAL_SERVER_ERROR);
    }
    
    @ExceptionHandler(CobroNotFoundException.class)
    public ResponseEntity<String> handleCobroNotFound(CobroNotFoundException ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
    }
}
