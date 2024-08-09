package com.example.demo.services.impl;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.entities.Book;
import com.example.demo.enums.ApiErrorMessageEnum;
import com.example.demo.models.mapinterface.BookMapper;
import com.example.demo.models.request.BookReqModel;
import com.example.demo.models.response.BookResModel;
import com.example.demo.services.BookService;
import com.example.demo.utils.BusinessLogicViolationException;
import com.example.demo.repositories.BookRepository;


@Service
public class DefaultBookService implements BookService{

	@Autowired
	BookRepository bookRepository;
	
	@Autowired
	BookMapper bookMapper;

	//-------------------------------------------------------
	
	@Override
	public BookResModel createBook(BookReqModel bookReqModel) {
		Book book = new Book();
		mapBookReqModel( bookReqModel,  book);
		bookRepository.save(book);
		return bookMapper.mapToBookResModel(book);
	}

	@Override
	@Transactional
	public BookResModel updateBookById(Long bookId, BookReqModel bookReqModel) {
		Optional<Book> book = bookRepository.findById(bookId);
		if(book.isEmpty())
			throw new BusinessLogicViolationException(ApiErrorMessageEnum.NO_MATCHING_BOOK_RECORD_FOR_THIS_ID.name());
		Book bookObject = book.get();
		mapBookReqModel(bookReqModel, bookObject);
		bookRepository.save(bookObject);
		return bookMapper.mapToBookResModel(bookObject);
	}

	@Override
	public BookResModel getBookById(Long bookId) {
		Optional<Book> book = bookRepository.findById(bookId);
		if(book.isEmpty())
			throw new BusinessLogicViolationException(ApiErrorMessageEnum.NO_MATCHING_BOOK_RECORD_FOR_THIS_ID.name());
		Book bookObject = book.get();
		return bookMapper.mapToBookResModel(bookObject);
	}

	@Override
	public List<BookResModel> getAllBooks() {
		List<Book> bookList = bookRepository.findAll();
		if(bookList.isEmpty())
			throw new BusinessLogicViolationException(ApiErrorMessageEnum.LIST_IS_EMPTY.name());
		return bookMapper.mapToBookResModelList(bookList);
	}
	
	@Override
	public void deleteBookById(Long bookId) {
		Optional<Book> book = bookRepository.findById(bookId);
		if(book.isEmpty())
			throw new BusinessLogicViolationException(ApiErrorMessageEnum.NO_MATCHING_BOOK_RECORD_FOR_THIS_ID.name());
		bookRepository.deleteById(bookId);
	}
	
	//-------------------------Helpers------------------------
	
	private void mapBookReqModel(BookReqModel bookReqModel, Book book) {
		book.setAuthor(bookReqModel.getAuthor());
		book.setIsbn(bookReqModel.getIsbn());
		book.setPublicationYear(bookReqModel.getPublicationYear());
		book.setTitle(bookReqModel.getTitle());
	}

}
