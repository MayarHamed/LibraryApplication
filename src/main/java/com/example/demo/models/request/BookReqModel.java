package com.example.demo.models.request;

import java.io.Serializable;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import lombok.Data;

@Data
public class BookReqModel implements Serializable{/**
	 * 
	 */
	private static final long serialVersionUID = -3110238236072302265L;

    @NotEmpty(message = "Title cannot be empty")
	private String title;
    
    @NotEmpty(message = "Author cannot be empty")
	private String author;
	
	 @Min(value = 1000, message = "Publication year must be a 4-digit year")
	 @Max(value = 9999, message = "Publication year must be a 4-digit year")
	 private Long publicationYear;
	
	@Size(max = 13)
	@Pattern(regexp = "^(?:\\d{9}[\\dX]|\\d{13})$")
    @NotEmpty(message = "ISBN cannot be empty")
	private String isbn;

}
