package com.example.demo.models.response;
import java.io.Serializable;

import lombok.Data;

@Data
public class BookResModel implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -6590586024629403535L;

	private long id;
	private String title;
	private String author;
	private long publicationYear;
	private String isbn;

}
