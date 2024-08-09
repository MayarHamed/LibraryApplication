package com.example.demo.services;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.demo.models.request.BookReqModel;
import com.example.demo.models.response.BookResModel;

@Service
public interface BookService {

	public BookResModel createBook(BookReqModel bookReqModel);
	public BookResModel updateBookById(Long bookId, BookReqModel bookReqModel);
	public BookResModel getBookById(Long bookId);
	public List<BookResModel> getAllBooks();
	public void deleteBookById(Long bookId);

}
