package com.target.myretail.exception;

import com.fasterxml.jackson.core.JsonParseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;

/**
 * Central place to handle exceptions including both
 * client errors and server errors.
 */

@ControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(DataInputException.class)
    public ResponseEntity<String> handleDataInputException(Exception ex) {
        LOGGER.error("input data not valid", ex);
        String errMsg = ex.getMessage();
        return new ResponseEntity<String>(ResponseMessageUtility.composeErrorMessage(errMsg), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ProductNotFoundException.class)
    public ResponseEntity<String> handleProductNotFoundException(Exception ex) {
        LOGGER.error("data not found", ex);
        String errMsg = ex.getMessage();
        return new ResponseEntity<String>(ResponseMessageUtility.composeErrorMessage(errMsg), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(JsonParseException.class)
    @ResponseBody
    public ResponseEntity<String> onJsonParseException(JsonParseException ex) {
        String errMsg ="could not parse request";
        LOGGER.error(errMsg, ex);
        return new ResponseEntity<String>(ResponseMessageUtility.composeErrorMessage(errMsg), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseBody
    public ResponseEntity<String> onConstraintValidationException(ConstraintViolationException e) {
        ValidationErrorResponse error = new ValidationErrorResponse();
        for (ConstraintViolation violation : e.getConstraintViolations()) {
            error.getDataInputErrors().add(
                    new DataInputError(violation.getPropertyPath().toString(), violation.getMessage()));
        }
        return new ResponseEntity<String>(ResponseMessageUtility.composeFromErrorList(error.getDataInputErrors()), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseBody
    public ResponseEntity<String> onMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        ValidationErrorResponse error = new ValidationErrorResponse();
        for (FieldError fieldError : e.getBindingResult().getFieldErrors()) {
            error.getDataInputErrors().add(
                    new DataInputError(fieldError.getField(), fieldError.getDefaultMessage()));
        }
        return new ResponseEntity<String>(ResponseMessageUtility.composeFromErrorList(error.getDataInputErrors()), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ServiceException.class)
    @ResponseBody
    public ResponseEntity<String> handleServiceException(ServiceException ex) {
        String errMsg ="server error: "+ex.getMessage();
        LOGGER.error(errMsg, ex);
        return new ResponseEntity<String>(ResponseMessageUtility.composeErrorMessage(errMsg), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(Exception.class)
    @ResponseBody
    public ResponseEntity<String> handleException(Exception ex) {
        String errMsg ="server error: "+ex.getMessage();
        LOGGER.error(errMsg, ex);
        return new ResponseEntity<String>(ResponseMessageUtility.composeErrorMessage(errMsg), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}