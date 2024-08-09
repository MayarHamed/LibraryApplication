package com.example.demo.models.mapinterface;

import java.util.List;

import org.mapstruct.Mapper;

import com.example.demo.entities.Book;
import com.example.demo.models.response.BookResModel;

@Mapper(componentModel = "spring")
public interface BookMapper {

	BookResModel mapToBookResModel(Book book);
	List<BookResModel> mapToBookResModelList(List<Book> book);

}
