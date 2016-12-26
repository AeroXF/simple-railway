package com.fengyunjie.railway.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.fengyunjie.railway.model.Train;

public interface TrainRepository extends JpaRepository<Train, Long>{

	@Query("SELECT DISTINCT trainNo FROM Train")
	List<String> getAllTrainNo();
	
	@Query("SELECT count(*) FROM Train WHERE trainNo = ?1")
	int countTrainNo(String trainNo);

	@Query
	List<Train> findByTrainNo(String trainNo);

	@Query("SELECT count(*) FROM Train WHERE trainNo=?1 AND statOrder=?2")
	int countByTrainNoAndStatOrder(String trainNo, Integer statOrder);

}
