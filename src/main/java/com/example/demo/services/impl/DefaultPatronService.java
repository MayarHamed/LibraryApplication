package com.example.demo.services.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.entities.Patron;
import com.example.demo.enums.ApiErrorMessageEnum;
import com.example.demo.models.mapinterface.PatronMapper;
import com.example.demo.models.request.PatronReqModel;
import com.example.demo.models.response.PatronResModel;
import com.example.demo.repositories.PatronRepository;
import com.example.demo.services.PatronService;
import com.example.demo.utils.BusinessLogicViolationException;


@Service
public class DefaultPatronService implements PatronService{

	@Autowired
	PatronRepository patronRepository;
	
	@Autowired
	PatronMapper patronMapper;
	
	//---------------------------------------------------------------
	
	@Override
	public PatronResModel createPatron(PatronReqModel patronReqModel) {
		Patron patron = new Patron();
		mapPatronReqModel(patronReqModel, patron);
		patronRepository.save(patron);
		return patronMapper.mapToPatronResModel(patron);
	}

	@Override
	public PatronResModel updatePatronById(Long patronId, PatronReqModel patronReqModel) {
		Optional<Patron> patron = patronRepository.findById(patronId);
		if(patron.isEmpty())
			throw new BusinessLogicViolationException(ApiErrorMessageEnum.NO_MATCHING_PATRON_RECORD_FOR_THIS_ID.name());
		Patron patronObject = patron.get();
		mapPatronReqModel(patronReqModel, patronObject);
		patronRepository.save(patronObject);
		return patronMapper.mapToPatronResModel(patronObject);
	}

	@Override
	public PatronResModel getPatronById(Long patronId) {
		Optional<Patron> patron = patronRepository.findById(patronId);
		if(patron.isEmpty())
			throw new BusinessLogicViolationException(ApiErrorMessageEnum.NO_MATCHING_PATRON_RECORD_FOR_THIS_ID.name());
		Patron patronObject = patron.get();
		return patronMapper.mapToPatronResModel(patronObject);
	}

	@Override
	public List<PatronResModel> getAllPatrons() {
		List<Patron> patronList = patronRepository.findAll();
		if(patronList.isEmpty())
			throw new BusinessLogicViolationException(ApiErrorMessageEnum.LIST_IS_EMPTY.name());
		return patronMapper.mapToPatronResModelList(patronList);
	}

	@Override
	public void deletePatronById(Long patronId) {
		Optional<Patron> patron = patronRepository.findById(patronId);
		if(patron.isEmpty())
			throw new BusinessLogicViolationException(ApiErrorMessageEnum.NO_MATCHING_PATRON_RECORD_FOR_THIS_ID.name());
		patronRepository.deleteById(patronId);
	}
	
	//-------------------------Helpers------------------------
	
	private void mapPatronReqModel(PatronReqModel patronReqModel, Patron patron) {
		patron.setEmail(patronReqModel.getEmail());
		patron.setMobile(patronReqModel.getMobile());
		patron.setName(patronReqModel.getName());
		patron.setAddress(patronReqModel.getAddress());
	}

}
