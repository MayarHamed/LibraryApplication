package com.example.demo.services;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.demo.models.request.PatronReqModel;
import com.example.demo.models.response.PatronResModel;

@Service
public interface PatronService {
	
	public PatronResModel createPatron(PatronReqModel patronReqModel);
	public PatronResModel updatePatronById(Long patronId, PatronReqModel patronReqModel);
	public PatronResModel getPatronById(Long patronId);
	public List<PatronResModel> getAllPatrons();
	public void deletePatronById(Long patronId);
	
}
