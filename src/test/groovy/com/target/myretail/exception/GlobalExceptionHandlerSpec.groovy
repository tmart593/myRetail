package com.target.myretail.exception

import com.fasterxml.jackson.core.JsonParseException
import org.springframework.http.ResponseEntity
import org.springframework.validation.BindingResult
import org.springframework.validation.FieldError
import org.springframework.web.bind.MethodArgumentNotValidException
import spock.lang.Specification

import javax.validation.ConstraintViolationException

class GlobalExceptionHandlerSpec extends Specification {
    def 'HandleProductNotFoundException'() {
        setup:
        GlobalExceptionHandler globalExceptionHandler = new GlobalExceptionHandler()
        String errMsg ='test this'
        ProductNotFoundException exception = new ProductNotFoundException(errMsg)
        when:
        ResponseEntity<String> responseEntity= globalExceptionHandler.handleProductNotFoundException(exception)
        then:
        responseEntity.body == ResponseMessageUtility.composeErrorMessage(errMsg)
    }

    def 'HandleDataInputException'() {
        setup:
        GlobalExceptionHandler globalExceptionHandler = new GlobalExceptionHandler()
        String errMsg ='test this'
        DataInputException exception = new DataInputException(errMsg)
        when:
        ResponseEntity<String> responseEntity= globalExceptionHandler.handleDataInputException(exception)
        then:
        responseEntity.body == ResponseMessageUtility.composeErrorMessage(errMsg)
    }

    def 'JsonParseException'() {
        setup:
        GlobalExceptionHandler globalExceptionHandler = new GlobalExceptionHandler()
        JsonParseException exception = Mock(JsonParseException)
        when:
        ResponseEntity<String> responseEntity= globalExceptionHandler.handleDataInputException(exception)
        then:
        responseEntity.body

    }

    def 'ConstraintViolationException'() {
        setup:
        GlobalExceptionHandler globalExceptionHandler = new GlobalExceptionHandler()
        ConstraintViolationException exception = Mock( ConstraintViolationException)
        when:
        ResponseEntity<String> responseEntity= globalExceptionHandler.handleDataInputException(exception)
        then:
        responseEntity.body

    }

    def 'MethodArgumentNotValidException'() {
        setup:
        GlobalExceptionHandler globalExceptionHandler = new GlobalExceptionHandler()
        String errMsg ='test this'

        FieldError fieldError = new FieldError("myobject", "myfield", errMsg);
        List<FieldError> fieldErrorList = new ArrayList<>()
        fieldErrorList.add(fieldError)

        BindingResult bindingResult = Mock(BindingResult)

        bindingResult.getFieldErrors(*_ ) >> fieldErrorList

        MethodArgumentNotValidException exception = Mock(MethodArgumentNotValidException)
        exception.getBindingResult() >> bindingResult

        when:
        ResponseEntity<String> responseEntity= globalExceptionHandler.onMethodArgumentNotValidException(exception)
        then:
        responseEntity.body.contains('{"fieldName":"myfield","message":"test this"}')

    }


    def 'ServiceException'() {
        setup:
        GlobalExceptionHandler globalExceptionHandler = new GlobalExceptionHandler()
        String errMsg ='internal server error processing request'
        ServiceException exception = new ServiceException(errMsg)
        when:
        ResponseEntity<String> responseEntity= globalExceptionHandler.handleServiceException(exception)
        then:
        responseEntity.body == ResponseMessageUtility.composeErrorMessage('server error: '+errMsg)

    }

    def 'Exception'() {
        setup:
        GlobalExceptionHandler globalExceptionHandler = new GlobalExceptionHandler()
        String errMsg ='test this'
        Exception exception = new Exception(errMsg)
        when:
        ResponseEntity<String> responseEntity= globalExceptionHandler.handleException(exception)
        then:
        responseEntity.body == ResponseMessageUtility.composeErrorMessage('server error: '+errMsg)

    }

}

