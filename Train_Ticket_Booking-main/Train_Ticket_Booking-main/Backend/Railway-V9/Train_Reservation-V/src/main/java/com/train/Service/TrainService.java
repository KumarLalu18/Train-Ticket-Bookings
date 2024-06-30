package com.train.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.train.Exception.TrainException;
import com.train.Repository.TrainRepository;
import com.train.model.Train;

@Service
public class TrainService {

	@Autowired
	private TrainRepository trainRepo;

	public List<Train> getAllTrains() throws TrainException {
		List<Train> trains = trainRepo.findAll();
		if (trains.isEmpty()) {
			throw new TrainException("No trains found in the database");
		}

		return trains;
	}

	public Optional<Train> getTrainById(Long id) throws TrainException {
		Optional<Train> existingTrain = trainRepo.findById(id);
		if (!existingTrain.isPresent()) {
			throw new TrainException("No train exist with this trainId: " + id);
		}

		return existingTrain;
	}

	public List<Train> getTrainsBySourceAndDestination(String source, String destination) throws TrainException {
		List<Train> trains = trainRepo.findBySourceAndDestination(source, destination);
		if (trains.isEmpty()) {
			throw new TrainException("No trains found with the given Source and Destination");
		}

		return trains;

	}

	public List<Train> getTrainsBySourceAndDestinationAndJourneyDate(String source, String destination,
			LocalDate journeyDate) throws TrainException {

		List<Train> trains = trainRepo.findBySourceAndDestinationAndJourneyDate(source, destination,
				journeyDate);
		if (trains.isEmpty()) {
			throw new TrainException("No trains found with the given Source, Destination and Date");
		}

		return trains;

	}

	public Train saveTrain(Train train) {
		return trainRepo.save(train);
	}

	public Train updateTrain(Train train) {
		return trainRepo.save(train);
	}

	public Train deleteTrain(Long id) throws TrainException {
		Train train = trainRepo.findById(id).orElseThrow(() -> new TrainException("Train not found for delete Train record"));
		// Delete the train from the database
		trainRepo.delete(train);

		return train;
	}

}