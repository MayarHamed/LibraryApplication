package com.example.demo.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.enums.ApiErrorMessageEnum;
import com.example.demo.models.request.PatronReqModel;
import com.example.demo.models.response.PatronResModel;
import com.example.demo.services.PatronService;
import com.example.demo.utils.BusinessLogicViolationException;

@RestController
@RequestMapping("/patrons")
public class PatronController {

	@Autowired
	PatronService patronService;

	// --------------------------------------------------------------------------------

	@PostMapping
	public ResponseEntity<PatronResModel> createPatron(@Valid @RequestBody PatronReqModel patronReqModel,
			BindingResult result) {
		if (result.hasErrors())
			throw new BusinessLogicViolationException(ApiErrorMessageEnum.DATA_VALIDATION_FAILED.name(),
					result.getAllErrors());
		try {
			return new ResponseEntity<>(patronService.createPatron(patronReqModel), HttpStatus.OK);
		} catch (DataIntegrityViolationException e) {
			throw new DataIntegrityViolationException(ApiErrorMessageEnum.UNIQUE_CONSTRAINT_VIOLATION.name());
		}
	}

	@PutMapping(path = "/{id}")
	public ResponseEntity<PatronResModel> updatePatronById(@Valid @RequestBody PatronReqModel patronReqModel,
			@PathVariable("id") Long patronId, BindingResult result) {
		if (result.hasErrors())
			throw new BusinessLogicViolationException(ApiErrorMessageEnum.DATA_VALIDATION_FAILED.name(),
					result.getAllErrors());
		try {
			return new ResponseEntity<>(patronService.updatePatronById(patronId, patronReqModel), HttpStatus.OK);
		} catch (DataIntegrityViolationException e) {
			throw new BusinessLogicViolationException(ApiErrorMessageEnum.UNIQUE_CONSTRAINT_VIOLATION.name(),
					result.getAllErrors());
		}
	}

	@GetMapping
	public ResponseEntity<List<PatronResModel>> getAllPatrons() {
		return new ResponseEntity<>(patronService.getAllPatrons(), HttpStatus.OK);
	}

	@GetMapping(path = "/{id}")
	public ResponseEntity<PatronResModel> getPatronById(@PathVariable("id") Long patronId) {
		return new ResponseEntity<>(patronService.getPatronById(patronId), HttpStatus.OK);
	}

	@DeleteMapping(path = "/{id}")
	public ResponseEntity<PatronResModel> deletePatronById(@PathVariable("id") Long patronId) {
		try {
			patronService.deletePatronById(patronId);
		} catch (Exception e) {
			// TODO: handle exception
		}
		return new ResponseEntity<>(HttpStatus.OK);
	}
}
