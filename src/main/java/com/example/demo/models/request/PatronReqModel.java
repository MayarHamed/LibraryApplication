package com.example.demo.models.request;

import java.io.Serializable;

import javax.validation.constraints.Email;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import lombok.Data;

@Data
public class PatronReqModel implements Serializable{/**
	 * 
	 */
	private static final long serialVersionUID = -4772163069039254681L;
	
	private String name;
	
	@Size(min = 11, max = 11)
	@Pattern(regexp = "^$|[0-9]+")
	private String mobile;
	
	@Email
	@Size(max = 50)
	@Pattern(regexp = "^$|^[A-Za-z0-9+_.-]+@(.+)$")
	private String email;
	
	private String address;

}
