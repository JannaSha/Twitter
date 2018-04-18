package com.gateway.controller;


import com.gateway.model.ErrorResponse;
import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import org.apache.log4j.Logger;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import javax.persistence.EntityNotFoundException;


@RestControllerAdvice(annotations = RestController.class)
public class ExceptionController {

    private static final Logger log = Logger.getLogger(ExceptionController.class);

    private String getMessage(String message, String altvMessage) {
        return  (message == null || message.isEmpty()) ? altvMessage : message;
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(EntityNotFoundException.class)
    public Object handleEntityNotFoundException(EntityNotFoundException exc) {
        log.info("[404] => " + exc.getMessage());
        return new ErrorResponse(getMessage(exc.getMessage(),
                "ERROR: Not found"),
                HttpStatus.NOT_FOUND);
    }

    @ResponseStatus(HttpStatus.CONFLICT)
    @ExceptionHandler(DataIntegrityViolationException.class)
    public ErrorResponse handleDataIntegtiryException(
            DataIntegrityViolationException exc) {
        log.info("[409] => " + exc.getMessage());
        return new ErrorResponse(getMessage(exc.getMessage(),
                "ERROR: Not found"),
                HttpStatus.CONFLICT);
    }

    @ResponseStatus(HttpStatus.METHOD_NOT_ALLOWED)
    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ErrorResponse handleRequestMethodNotSupportedException(
            HttpRequestMethodNotSupportedException exc) {
        log.info("[405] => " + exc.getMessage());
        return new ErrorResponse(getMessage(exc.getMessage(),
                "ERROR: Method is not allowed"),
                HttpStatus.METHOD_NOT_ALLOWED);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MissingServletRequestParameterException.class)
    public ErrorResponse handleRequestParamException(
            MissingServletRequestParameterException exc) {
        log.info("[405] => " + exc.getMessage());
        return new ErrorResponse(getMessage(exc.getMessage(),
                "ERROR: Missing request parameter"),
                HttpStatus.BAD_REQUEST);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ErrorResponse handleMallformedJsonError(
            HttpMessageNotReadableException exc) {
        log.info("[405] => " + exc.getMessage());
        return new ErrorResponse(getMessage(exc.getMessage(),
                "ERROR: Malformed JSON. Please check request body and try again"),
                HttpStatus.BAD_REQUEST);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ErrorResponse handleMethodArgumentTypeException(
            MethodArgumentTypeMismatchException exc) {
        log.info("[405] => " + exc.getMessage());
        return new ErrorResponse(getMessage(exc.getMessage(),
                "ERROR: Invalid method argument type"),
                HttpStatus.BAD_REQUEST);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ErrorResponse handleMethodArgumentNotValidException(
            MethodArgumentNotValidException exc) {
        log.info("[405] => " + exc.getMessage());
        return new ErrorResponse(
                "Error format",
//                exc.getBindingResult().findEditor(null, null).toString(),
                HttpStatus.BAD_REQUEST);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(InvalidFormatException.class)
    public ErrorResponse JSONValidException(InvalidFormatException exc) {
        log.info("[405] => " + exc.getMessage());
        return new ErrorResponse(
                "Error format",
//                exc.getMessage(),
                HttpStatus.BAD_REQUEST);
    }

//    @ResponseStatus(HttpStatus.BAD_REQUEST)
//    @ExceptionHandler(.class)
//    public ErrorResponse ResolverValidException(ExceptionHandlerExceptionResolver exc) {
//        log.info("[405] => " + exc.getMessage());
//        return new ErrorResponse(
//                "Error format",
////                exc.getMessage(),
//                HttpStatus.BAD_REQUEST);
//    }
}

