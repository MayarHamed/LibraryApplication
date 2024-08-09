package com.example.demo.models.response;


import java.io.Serializable;
import java.time.LocalDateTime;

import lombok.Data;

@Data
public class BorrowingRecordResModel implements Serializable{/**
	 * 
	 */
	private static final long serialVersionUID = -9026973430010525255L;

	private long id;
	private BookResModel book;
	private PatronResModel patron;
	private LocalDateTime borrowDate;
	private LocalDateTime returnDate;

}
