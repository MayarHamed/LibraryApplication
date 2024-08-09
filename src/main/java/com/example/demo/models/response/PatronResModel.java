package com.example.demo.models.response;

import java.io.Serializable;

import lombok.Data;

@Data
public class PatronResModel implements Serializable{/**
	 * 
	 */
	private static final long serialVersionUID = -810449936387833033L;
	
	private long id;
	private String email;
	private String mobile;
	private String name;
	private String address;

}
