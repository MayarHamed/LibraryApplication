package com.example.demo.service;

import com.example.demo.entities.Patron;
import com.example.demo.enums.ApiErrorMessageEnum;
import com.example.demo.models.mapinterface.PatronMapper;
import com.example.demo.models.request.PatronReqModel;
import com.example.demo.models.response.PatronResModel;
import com.example.demo.repositories.PatronRepository;
import com.example.demo.services.PatronService;
import com.example.demo.services.impl.DefaultPatronService;
import com.example.demo.utils.BusinessLogicViolationException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class PatronServiceTest {

	@InjectMocks
	private DefaultPatronService patronService;

	@Mock
	private PatronRepository patronRepository;

	@Mock
	private PatronMapper patronMapper;

	private Patron patron;
	private PatronReqModel patronReqModel;
	private PatronResModel patronResModel;

	@BeforeEach
	public void setup() {
		MockitoAnnotations.openMocks(this);

		patron = new Patron();
		patron.setId(1L);
		patron.setName("Marlyin Monro");

		patronReqModel = new PatronReqModel();
		patronReqModel.setName("Marlyin Monro");

		patronResModel = new PatronResModel();
		patronResModel.setId(1L);
		patronResModel.setName("Marlyin Monro");
	}

	@Test
	public void testCreatePatron_Success() {
		when(patronMapper.mapPatronReqModelToPatron(any(PatronReqModel.class))).thenReturn(patron);
		when(patronRepository.save(any(Patron.class))).thenReturn(patron);
		when(patronMapper.mapToPatronResModel(any(Patron.class))).thenReturn(patronResModel);

		PatronResModel result = patronService.createPatron(patronReqModel);

		assertNotNull(result);
		assertEquals("Marlyin Monro", result.getName());
		verify(patronRepository, times(1)).save(any(Patron.class));
	}

	@Test
	public void testUpdatePatronById_Success() {
		when(patronRepository.findById(anyLong())).thenReturn(Optional.of(patron));
		when(patronMapper.mapPatronReqModelToPatron(any(PatronReqModel.class))).thenReturn(patron);
		when(patronRepository.save(any(Patron.class))).thenReturn(patron);
		when(patronMapper.mapToPatronResModel(any(Patron.class))).thenReturn(patronResModel);

		PatronResModel result = patronService.updatePatronById(1L, patronReqModel);

		assertNotNull(result);
		assertEquals("Marlyin Monro", result.getName());
		verify(patronRepository, times(1)).save(any(Patron.class));
	}

	@Test
	public void testUpdatePatronById_PatronNotFound() {
		when(patronRepository.findById(anyLong())).thenReturn(Optional.empty());

		BusinessLogicViolationException exception = assertThrows(BusinessLogicViolationException.class, () ->
				patronService.updatePatronById(1L, patronReqModel));

		assertEquals(ApiErrorMessageEnum.NO_MATCHING_PATRON_RECORD_FOR_THIS_ID.name(), exception.getMessage());
	}

	@Test
	public void testGetPatronById_Success() {
		when(patronRepository.findById(anyLong())).thenReturn(Optional.of(patron));
		when(patronMapper.mapToPatronResModel(any(Patron.class))).thenReturn(patronResModel);

		PatronResModel result = patronService.getPatronById(1L);

		assertNotNull(result);
		assertEquals("Marlyin Monro", result.getName());
		verify(patronRepository, times(1)).findById(anyLong());
	}

	@Test
	public void testGetPatronById_PatronNotFound() {
		when(patronRepository.findById(anyLong())).thenReturn(Optional.empty());

		BusinessLogicViolationException exception = assertThrows(BusinessLogicViolationException.class, () ->
				patronService.getPatronById(1L));

		assertEquals(ApiErrorMessageEnum.NO_MATCHING_PATRON_RECORD_FOR_THIS_ID.name(), exception.getMessage());
	}

	@Test
	public void testGetAllPatrons_Success() {
		List<Patron> patrons = new ArrayList<>();
		patrons.add(patron);

		when(patronRepository.findAll()).thenReturn(patrons);
		when(patronMapper.mapToPatronResModelList(anyList())).thenReturn(List.of(patronResModel));

		List<PatronResModel> result = patronService.getAllPatrons();

		assertNotNull(result);
		assertFalse(result.isEmpty());
		assertEquals(1, result.size());
		assertEquals("Marlyin Monro", result.get(0).getName());
		verify(patronRepository, times(1)).findAll();
	}

	@Test
	public void testGetAllPatrons_EmptyList() {
		when(patronRepository.findAll()).thenReturn(new ArrayList<>());

		BusinessLogicViolationException exception = assertThrows(BusinessLogicViolationException.class, () ->
				patronService.getAllPatrons());

		assertEquals(ApiErrorMessageEnum.LIST_IS_EMPTY.name(), exception.getMessage());
	}

	@Test
	public void testDeletePatronById_Success() {
		when(patronRepository.findById(anyLong())).thenReturn(Optional.of(patron));

		patronService.deletePatronById(1L);

		verify(patronRepository, times(1)).deleteById(anyLong());
	}

	@Test
	public void testDeletePatronById_PatronNotFound() {
		when(patronRepository.findById(anyLong())).thenReturn(Optional.empty());

		BusinessLogicViolationException exception = assertThrows(BusinessLogicViolationException.class, () ->
				patronService.deletePatronById(1L));

		assertEquals(ApiErrorMessageEnum.NO_MATCHING_PATRON_RECORD_FOR_THIS_ID.name(), exception.getMessage());
	}
}
