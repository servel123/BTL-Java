/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.project.fashion.exception;

import java.util.Date;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

/**
 *
 * @author Vu
 */
@RestControllerAdvice
public class GlobalExceptionHandler {
    
    /**
     *
     * @param e
     * @param req
     * @return
     */
    @ExceptionHandler({MethodArgumentNotValidException.class,ConstraintViolationException.class })//handle Exception Validate Data code 4xx and 5xx
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handlerValidationException(Exception e, WebRequest req){
        ErrorResponse errorResponse = new ErrorResponse();
        errorResponse.setTimestamp(new Date());
        errorResponse.setStatus(HttpStatus.BAD_REQUEST.value());
        errorResponse.setPath(req.getDescription(false).replace("uri", ""));
        String message = e.getMessage();
        if(e instanceof MethodArgumentNotValidException){
            int start = e.getMessage().indexOf("[");
            int end = e.getMessage().indexOf("]");
            errorResponse.setError( "Payload Invalid");
            message = message.substring(start+1,end-1);
        }else if(e instanceof ConstraintViolationException){
            message = message.substring(e.getMessage().indexOf(" ")+1);
            errorResponse.setError( "PathVariable Invalid");
        }
        errorResponse.setMessage(message);
        return errorResponse;
    }
    
    @ExceptionHandler({MethodArgumentTypeMismatchException.class })//handle Exception 5xx
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ErrorResponse handlerInternalServerErrorException(Exception e, WebRequest req){
        ErrorResponse errorResponse = new ErrorResponse();
        errorResponse.setTimestamp(new Date());
        errorResponse.setStatus(HttpStatus.BAD_REQUEST.value());
        errorResponse.setPath(req.getDescription(false).replace("uri", ""));
        errorResponse.setError(HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase());
        if(e instanceof MethodArgumentTypeMismatchException){      
           errorResponse.setMessage("Failed to convert value of type");   
        }
        return errorResponse;
    }
}
