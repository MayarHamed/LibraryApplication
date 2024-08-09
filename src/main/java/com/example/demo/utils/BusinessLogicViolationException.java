package com.example.demo.utils;

import java.util.List;
import java.util.Map;

import org.springframework.validation.ObjectError;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=false)
public class BusinessLogicViolationException extends RuntimeException{

	/**
	 * 
	 */
	private static final long serialVersionUID = -3144639908025519246L;

	private List<ObjectError> details;

	public BusinessLogicViolationException(String message, List<ObjectError> details) {
		super(message);
		this.setDetails(details);
	}

	public BusinessLogicViolationException(String message) {
		super(message);
	}
}
