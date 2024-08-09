package com.example.demo.services.impl;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.entities.Book;
import com.example.demo.entities.BorrowingRecord;
import com.example.demo.entities.Patron;
import com.example.demo.enums.ApiErrorMessageEnum;
import com.example.demo.models.mapinterface.BorrowingRecordMapper;
import com.example.demo.models.request.BorrowingRecordReqModel;
import com.example.demo.models.response.BorrowingRecordResModel;
import com.example.demo.repositories.BookRepository;
import com.example.demo.repositories.BorrowingRecordRepository;
import com.example.demo.repositories.PatronRepository;
import com.example.demo.utils.BusinessLogicViolationException;


@Service
public class DefaultBorrowingRecordService implements com.example.demo.services.BorrowingRecordService {


	@Autowired
	BorrowingRecordRepository borrowingRecordRepository;
	
	@Autowired
	BorrowingRecordMapper borrowingRecordMapper;
	
	@Autowired
	BookRepository bookRepository;
	
	@Autowired
	PatronRepository patronRepository;
	
	//---------------------------------------------------------------
	
	@Override
	public BorrowingRecordResModel borrowBook(Long bookId,  Long patronId) {
		BorrowingRecord borrowingRecord = new BorrowingRecord();
		mapBorrowingRecordReqModel(bookId, patronId, borrowingRecord);
		borrowingRecordRepository.save(borrowingRecord);
		return borrowingRecordMapper.mapToBorrowingRecordResModel(borrowingRecord);
	}

	@Override
	public BorrowingRecordResModel returnBook(Long bookId,  Long patronId) {
		BorrowingRecord borrowingRecord = borrowingRecordRepository.findByBookIdAndPatronId(bookId, patronId);
		if(borrowingRecord == null)
			throw new BusinessLogicViolationException(ApiErrorMessageEnum.NO_MATCHING_BORROW_RECORD_FOR_THIS_ID.name());
		
		borrowingRecord.setReturnDate(LocalDateTime.now());
		borrowingRecordRepository.save(borrowingRecord);
		return borrowingRecordMapper.mapToBorrowingRecordResModel(borrowingRecord);
	}
	
	//-------------------------Helpers------------------------
	
	private void mapBorrowingRecordReqModel(Long bookId,  Long patronId, BorrowingRecord borrowingRecord) {
		
		Optional<Book> book = bookRepository.findById(bookId);
		if(book.isEmpty())
			throw new BusinessLogicViolationException(ApiErrorMessageEnum.NO_MATCHING_BOOK_RECORD_FOR_THIS_ID.name());
		borrowingRecord.setBook(book.get());
		
		Optional<Patron> patron = patronRepository.findById(patronId);
		if(patron.isEmpty())
			throw new BusinessLogicViolationException(ApiErrorMessageEnum.NO_MATCHING_PATRON_RECORD_FOR_THIS_ID.name());
		borrowingRecord.setPatron(patron.get());
		
		borrowingRecord.setBorrowDate(LocalDateTime.now());

	}

}

