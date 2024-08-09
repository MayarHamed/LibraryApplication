package com.example.demo.models.request;

import java.io.Serializable;
import java.time.LocalDateTime;

import lombok.Data;

@Data
public class BorrowingRecordReqModel implements Serializable{/**
	 * 
	 */
	private static final long serialVersionUID = -347929919164549346L;

	private long bookId;
	private long patronId;
	private LocalDateTime borrowDate;
	private LocalDateTime returnDate;

}
