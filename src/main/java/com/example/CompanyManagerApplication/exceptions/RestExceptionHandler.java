package com.example.CompanyManagerApplication.exceptions;

import com.example.CompanyManagerApplication.models.ApiError;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.TransactionSystemException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.persistence.EntityNotFoundException;

@ControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<Object> handleEntityNotFoundException(EntityNotFoundException ex, WebRequest request) {
        String message = ex.getMessage();
        ApiError apiError = new ApiError(HttpStatus.NOT_FOUND, message, request.getDescription(false));
        return new ResponseEntity<>(apiError, apiError.getStatus());
    }

    @ExceptionHandler(EntityAlreadyExistsException.class)
    public ResponseEntity<Object> handleEntityAlreadyExistsException(EntityAlreadyExistsException ex, WebRequest request) {
        String message = ex.getMessage();
        ApiError apiError = new ApiError(HttpStatus.CONFLICT, message, request.getDescription(false));
        return new ResponseEntity<>(apiError, apiError.getStatus());
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<Object> handleIllegalArgumentException(IllegalArgumentException ex, WebRequest request) {
        String message = ex.getMessage();
        ApiError apiError = new ApiError(HttpStatus.CONFLICT, message, request.getDescription(false));
        return new ResponseEntity<>(apiError, apiError.getStatus());
    }

    @ExceptionHandler(TransactionSystemException.class)
    public ResponseEntity<Object> handleTransactionSystemException(TransactionSystemException ex, WebRequest request) {
        String message = ex.getMessage();
        ApiError apiError = new ApiError(HttpStatus.CONFLICT, message, request.getDescription(false));
        return new ResponseEntity<>(apiError, apiError.getStatus());
    }
}