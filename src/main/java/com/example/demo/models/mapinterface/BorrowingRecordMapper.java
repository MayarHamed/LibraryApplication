package com.example.demo.models.mapinterface;

import java.util.List;

import org.mapstruct.Mapper;

import com.example.demo.entities.BorrowingRecord;
import com.example.demo.models.response.BorrowingRecordResModel;


@Mapper(componentModel = "spring", uses= {BookMapper.class, PatronMapper.class})
public interface BorrowingRecordMapper {

	BorrowingRecordResModel mapToBorrowingRecordResModel(BorrowingRecord borrowingRecord);
	List<BorrowingRecordResModel> mapToBorrowingRecordResModelList(List<BorrowingRecord> borrowingRecord);

}
