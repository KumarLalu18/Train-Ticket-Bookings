package com.train.Repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.train.model.Train;

@Repository
public interface TrainRepository extends JpaRepository<Train, Long> {

	List<Train> findBySourceAndDestination(String source, String destination);

	void save(List<Train> train);

	List<Train> findBySourceAndDestinationAndJourneyDate(String source, String destination, LocalDate journeyDate);


}
