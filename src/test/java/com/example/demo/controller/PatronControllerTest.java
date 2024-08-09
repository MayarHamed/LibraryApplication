package com.example.demo.controller;

import com.example.demo.models.request.PatronReqModel;
import com.example.demo.models.response.PatronResModel;
import com.example.demo.services.PatronService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(PatronController.class)
public class PatronControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PatronService patronService;

    private PatronResModel patronResModel;

    @BeforeEach
    public void setup() {
        patronResModel = new PatronResModel();
        patronResModel.setId(1L);
        patronResModel.setName("Jane Smith");
        patronResModel.setEmail("jane.smith@example.com");
        patronResModel.setMobile("01222222222");
    }

    @Test
    public void testCreatePatron() throws Exception {
        when(patronService.createPatron(any(PatronReqModel.class))).thenReturn(patronResModel);

        String requestBody = "{ \"name\": \"Jane Smith\", \"email\": \"jane.smith@example.com\", \"mobile\": \"01222222222\" }";

        mockMvc.perform(post("/patrons")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.name").value("Jane Smith"))
                .andExpect(jsonPath("$.email").value("jane.smith@example.com"))
                .andExpect(jsonPath("$.mobile").value("01222222222"));

        verify(patronService, times(1)).createPatron(any(PatronReqModel.class));
    }

    @Test
    public void testUpdatePatronById() throws Exception {
        when(patronService.updatePatronById(anyLong(), any(PatronReqModel.class))).thenReturn(patronResModel);

        String requestBody = "{ \"name\": \"Jane Smith\", \"email\": \"jane.smith@example.com\", \"mobile\": \"01222222222\" }";

        mockMvc.perform(put("/patrons/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.name").value("Jane Smith"))
                .andExpect(jsonPath("$.email").value("jane.smith@example.com"))
                .andExpect(jsonPath("$.mobile").value("01222222222"));

        verify(patronService, times(1)).updatePatronById(anyLong(), any(PatronReqModel.class));
    }

    @Test
    public void testGetAllPatrons() throws Exception {
        List<PatronResModel> patrons = Arrays.asList(patronResModel);
        when(patronService.getAllPatrons()).thenReturn(patrons);

        mockMvc.perform(get("/patrons")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1L))
                .andExpect(jsonPath("$[0].name").value("Jane Smith"))
                .andExpect(jsonPath("$[0].email").value("jane.smith@example.com"))
                .andExpect(jsonPath("$[0].mobile").value("01222222222"));

        verify(patronService, times(1)).getAllPatrons();
    }

    @Test
    public void testGetPatronById() throws Exception {
        when(patronService.getPatronById(anyLong())).thenReturn(patronResModel);

        mockMvc.perform(get("/patrons/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.name").value("Jane Smith"))
                .andExpect(jsonPath("$.email").value("jane.smith@example.com"))
                .andExpect(jsonPath("$.mobile").value("01222222222"));

        verify(patronService, times(1)).getPatronById(anyLong());
    }

    @Test
    public void testDeletePatronById() throws Exception {
        doNothing().when(patronService).deletePatronById(anyLong());

        mockMvc.perform(delete("/patrons/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        verify(patronService, times(1)).deletePatronById(anyLong());
    }
}
