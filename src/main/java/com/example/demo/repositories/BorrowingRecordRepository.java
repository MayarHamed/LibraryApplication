package com.example.demo.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.entities.BorrowingRecord;

@Repository

public interface BorrowingRecordRepository extends JpaRepository<BorrowingRecord, Long>{

	BorrowingRecord findByBookIdAndPatronId(Long bookId,  Long patronId);
}
