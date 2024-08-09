package com.example.demo.controller;

import com.example.demo.models.response.BookResModel;
import com.example.demo.models.response.BorrowingRecordResModel;
import com.example.demo.models.response.PatronResModel;
import com.example.demo.services.BorrowingRecordService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@WebMvcTest(BorrowingRecordController.class)
public class BorrowingRecordControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private BorrowingRecordService borrowingRecordService;

    private BorrowingRecordResModel borrowingRecordResModel;
    private BookResModel book;
    private PatronResModel patron;

    private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");

    @BeforeEach
    public void setup() {

        book = new BookResModel();
        book.setId(1L);
        book.setTitle("Test Book");
        book.setAuthor("Test Author");
        book.setIsbn("123456789");
        book.setPublicationYear(2020);

        patron = new PatronResModel();
        patron.setId(1L);
        patron.setName("Test Patron");
        patron.setEmail("testEmail@test.com");
        patron.setMobile("01111111111");

        borrowingRecordResModel = new BorrowingRecordResModel();
        borrowingRecordResModel.setId(1L);
        borrowingRecordResModel.setBook(book);
        borrowingRecordResModel.setPatron(patron);
        borrowingRecordResModel.setBorrowDate(LocalDateTime.of(2024, 6, 9, 10, 0, 0));
        borrowingRecordResModel.setReturnDate(null);
    }

    @Test
    public void testCreateBorrowingRecord() throws Exception {
        when(borrowingRecordService.borrowBook(anyLong(), anyLong())).thenReturn(borrowingRecordResModel);

        mockMvc.perform(post("/borrow/1/patron/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.book.id").value(1L))
                .andExpect(jsonPath("$.patron.id").value(1L))
                .andExpect(jsonPath("$.borrowDate").value(borrowingRecordResModel.getBorrowDate().format(formatter)));

        verify(borrowingRecordService, times(1)).borrowBook(anyLong(), anyLong());
    }

    @Test
    public void testUpdateBorrowingRecordById() throws Exception {
        borrowingRecordResModel.setReturnDate(LocalDateTime.of(2024, 8, 9, 10, 0, 0));
        when(borrowingRecordService.returnBook(anyLong(), anyLong())).thenReturn(borrowingRecordResModel);

        mockMvc.perform(put("/return/1/patron/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.book.id").value(1L))
                .andExpect(jsonPath("$.patron.id").value(1L))
                .andExpect(jsonPath("$.borrowDate").value(borrowingRecordResModel.getBorrowDate().format(formatter)))
                .andExpect(jsonPath("$.returnDate").value(borrowingRecordResModel.getReturnDate().format(formatter)));

        verify(borrowingRecordService, times(1)).returnBook(anyLong(), anyLong());
    }
}
