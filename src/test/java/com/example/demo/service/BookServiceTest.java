package com.example.demo.service;

import com.example.demo.entities.Book;
import com.example.demo.enums.ApiErrorMessageEnum;
import com.example.demo.models.mapinterface.BookMapper;
import com.example.demo.models.request.BookReqModel;
import com.example.demo.models.response.BookResModel;
import com.example.demo.repositories.BookRepository;
import com.example.demo.services.BookService;
import com.example.demo.services.impl.DefaultBookService;
import com.example.demo.utils.BusinessLogicViolationException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class BookServiceTest {

    @Mock
    private BookRepository bookRepository;

    @Mock
    private BookMapper bookMapper;

    @InjectMocks
    private DefaultBookService bookService;

    private BookReqModel bookReqModel;
    private Book book;
    private BookResModel bookResModel;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        bookReqModel = new BookReqModel();
        bookReqModel.setTitle("Test Book");
        bookReqModel.setAuthor("Test Author");
        bookReqModel.setIsbn("123456789x");
        bookReqModel.setPublicationYear(2020L);

        book = new Book();
        book.setId(1L);
        book.setTitle("Test Book");
        book.setAuthor("Test Author");
        book.setIsbn("123456789x");
        book.setPublicationYear(2020L);

        bookResModel = new BookResModel();
        bookResModel.setId(1L);
        bookResModel.setTitle("Test Book");
        bookResModel.setAuthor("Test Author");
        bookResModel.setIsbn("123456789x");
        bookResModel.setPublicationYear(2020L);
    }

    @Test
    void createBook_success() {
        when(bookMapper.mapBookReqModelToBook(any(BookReqModel.class))).thenReturn(book);
        when(bookRepository.save(any(Book.class))).thenReturn(book);
        when(bookMapper.mapToBookResModel(any(Book.class))).thenReturn(bookResModel);

        BookResModel result = bookService.createBook(bookReqModel);

        assertNotNull(result);
        assertEquals(bookResModel.getTitle(), result.getTitle());
        verify(bookRepository, times(1)).save(any(Book.class));
    }

    @Test
    void updateBookById_success() {
        when(bookRepository.findById(anyLong())).thenReturn(Optional.of(book));
        when(bookMapper.mapBookReqModelToBook(any(BookReqModel.class))).thenReturn(book);
        when(bookRepository.save(any(Book.class))).thenReturn(book);
        when(bookMapper.mapToBookResModel(any(Book.class))).thenReturn(bookResModel);

        BookResModel result = bookService.updateBookById(1L, bookReqModel);

        assertNotNull(result);
        assertEquals(bookResModel.getTitle(), result.getTitle());
        verify(bookRepository, times(1)).findById(anyLong());
        verify(bookRepository, times(1)).save(any(Book.class));
    }

    @Test
    void updateBookById_noBookFound() {
        when(bookRepository.findById(anyLong())).thenReturn(Optional.empty());

        BusinessLogicViolationException exception = assertThrows(BusinessLogicViolationException.class, () -> {
            bookService.updateBookById(1L, bookReqModel);
        });

        assertEquals(ApiErrorMessageEnum.NO_MATCHING_BOOK_RECORD_FOR_THIS_ID.name(), exception.getMessage());
        verify(bookRepository, times(1)).findById(anyLong());
        verify(bookRepository, never()).save(any(Book.class));
    }

    @Test
    void getBookById_success() {
        when(bookRepository.findById(anyLong())).thenReturn(Optional.of(book));
        when(bookMapper.mapToBookResModel(any(Book.class))).thenReturn(bookResModel);

        BookResModel result = bookService.getBookById(1L);

        assertNotNull(result);
        assertEquals(bookResModel.getTitle(), result.getTitle());
        verify(bookRepository, times(1)).findById(anyLong());
    }

    @Test
    void getBookById_noBookFound() {
        when(bookRepository.findById(anyLong())).thenReturn(Optional.empty());

        BusinessLogicViolationException exception = assertThrows(BusinessLogicViolationException.class, () -> {
            bookService.getBookById(1L);
        });

        assertEquals(ApiErrorMessageEnum.NO_MATCHING_BOOK_RECORD_FOR_THIS_ID.name(), exception.getMessage());
        verify(bookRepository, times(1)).findById(anyLong());
    }

    @Test
    void getAllBooks_success() {
        List<Book> books = new ArrayList<>();
        books.add(book);

        when(bookRepository.findAll()).thenReturn(books);
        when(bookMapper.mapToBookResModelList(anyList())).thenReturn(List.of(bookResModel));

        List<BookResModel> result = bookService.getAllBooks();

        assertNotNull(result);
        assertEquals(1, result.size());
        verify(bookRepository, times(1)).findAll();
    }

    @Test
    void getAllBooks_noBooksFound() {
        when(bookRepository.findAll()).thenReturn(new ArrayList<>());

        BusinessLogicViolationException exception = assertThrows(BusinessLogicViolationException.class, () -> {
            bookService.getAllBooks();
        });

        assertEquals(ApiErrorMessageEnum.LIST_IS_EMPTY.name(), exception.getMessage());
        verify(bookRepository, times(1)).findAll();
    }

    @Test
    void deleteBookById_success() {
        when(bookRepository.findById(anyLong())).thenReturn(Optional.of(book));

        bookService.deleteBookById(1L);

        verify(bookRepository, times(1)).findById(anyLong());
        verify(bookRepository, times(1)).deleteById(anyLong());
    }

    @Test
    void deleteBookById_noBookFound() {
        when(bookRepository.findById(anyLong())).thenReturn(Optional.empty());

        BusinessLogicViolationException exception = assertThrows(BusinessLogicViolationException.class, () -> {
            bookService.deleteBookById(1L);
        });

        assertEquals(ApiErrorMessageEnum.NO_MATCHING_BOOK_RECORD_FOR_THIS_ID.name(), exception.getMessage());
        verify(bookRepository, times(1)).findById(anyLong());
        verify(bookRepository, never()).deleteById(anyLong());
    }
}