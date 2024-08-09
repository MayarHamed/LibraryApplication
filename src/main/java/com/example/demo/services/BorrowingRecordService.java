package com.example.demo.services;


import org.springframework.stereotype.Service;

import com.example.demo.models.response.BorrowingRecordResModel;

@Service
public interface BorrowingRecordService {

	public BorrowingRecordResModel borrowBook( Long bookId,  Long patronId);
	public BorrowingRecordResModel returnBook( Long bookId,  Long patronId);

}
