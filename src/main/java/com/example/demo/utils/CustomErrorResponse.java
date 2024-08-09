package com.example.demo.utils;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.validation.ObjectError;

import lombok.Data;

@Data
public class CustomErrorResponse {
	private LocalDateTime timestamp;
	private String message;
	private String path;
	private List<ObjectError> details;

	public CustomErrorResponse(String message, String path, List<ObjectError> details) {
		this.message = message;
		this.path = path;
		this.timestamp = LocalDateTime.now();
		this.details = details;
	}

	public CustomErrorResponse(String message) {
		this.message = message;
		this.timestamp = LocalDateTime.now();

	}
	
	public CustomErrorResponse(String message,  String path) {
		this.message = message;
		this.timestamp = LocalDateTime.now();
		this.path = path;

	}

}
