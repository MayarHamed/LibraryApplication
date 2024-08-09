package com.example.demo.utils;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

@ControllerAdvice
public class GlobalExceptionHandler {


    @ExceptionHandler(Exception.class)
    @ResponseBody
    public ResponseEntity<CustomErrorResponse> handleException(Exception ex, HttpServletRequest request) {
        String path = request.getRequestURI();
        
        CustomErrorResponse errorResponse = new CustomErrorResponse(ex.getMessage(), path);
        return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }
    
    @ExceptionHandler(BusinessLogicViolationException.class)
    @ResponseBody
    public ResponseEntity<CustomErrorResponse> handleBusinessLogicViolationException(BusinessLogicViolationException ex, HttpServletRequest request) {
        String message = ex.getMessage();
        String path = request.getRequestURI();
        List<ObjectError> details = ex.getDetails();
        CustomErrorResponse errorResponse = new CustomErrorResponse(message, path,details);
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }
    
    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<CustomErrorResponse> handleDataIntegrityViolationException(DataIntegrityViolationException ex) {
        String message = ex.getMessage();
        CustomErrorResponse errorResponse = new CustomErrorResponse(message);

    	return new ResponseEntity<>(errorResponse, HttpStatus.CONFLICT);
    }

}

