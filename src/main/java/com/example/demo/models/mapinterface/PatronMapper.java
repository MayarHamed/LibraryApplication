package com.example.demo.models.mapinterface;

import java.util.List;

import org.mapstruct.Mapper;

import com.example.demo.entities.Patron;
import com.example.demo.models.request.PatronReqModel;
import com.example.demo.models.response.PatronResModel;


@Mapper(componentModel = "spring")
public interface PatronMapper {
	
	PatronResModel mapToPatronResModel(Patron patron);
	List<PatronResModel> mapToPatronResModelList(List<Patron> patron);
	Patron mapPatronReqModelToPatron(PatronReqModel patronReqModel);

}
