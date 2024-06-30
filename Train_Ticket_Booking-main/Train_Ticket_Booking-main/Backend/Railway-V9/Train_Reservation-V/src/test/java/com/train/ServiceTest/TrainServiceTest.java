package com.train.ServiceTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.train.Exception.TrainException;
import com.train.Repository.TrainRepository;
import com.train.Service.TrainService;
import com.train.model.Train;

public class TrainServiceTest {

	@Mock
	private TrainRepository trainRepository;

	@InjectMocks
	private TrainService trainService;

	@SuppressWarnings("deprecation")
	@BeforeEach
	public void init() {
		MockitoAnnotations.initMocks(this);
	}

	@Test
	public void testGetAllTrains() throws TrainException {
		List<Train> trains = new ArrayList<>();
		trains.add(new Train(1L, "A Express", "AC", "Pune", "Chennai", LocalDate.now(), null, null, 1000, 50, 50, null));
		trains.add(new Train(2L, "B Express", "SL", "Pune", "Chennai", LocalDate.now(), null, null, 500, 100, 100, null));

		when(trainRepository.findAll()).thenReturn(trains);

		List<Train> result = trainService.getAllTrains();

		assertEquals(2, result.size());
	}

	@Test
	public void testGetTrainById() throws TrainException {
		Train train = new Train(1L, "A Express", "AC", "Pune", "Chennai", LocalDate.now(), null, null, 1000, 50, 50, null);

		when(trainRepository.findById(1L)).thenReturn(Optional.of(train));

		Optional<Train> result = trainService.getTrainById(1L);

		assertEquals("A Express", result.get().getTrainName());
	}

	@Test
    public void testGetTrainByIdNotFound() {
        when(trainRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(TrainException.class, () -> trainService.getTrainById(1L));
    }

	@Test
	public void testGetTrainsBySourceAndDestination() throws TrainException {
		List<Train> trains = new ArrayList<>();
		trains.add(new Train(1L, "A Express", "AC", "Pune", "Chennai", LocalDate.now(), null, null, 100, 50, 50, null));
		trains.add(new Train(2L, "B Express", "SL", "Pune", "Chennai", LocalDate.now(), null, null, 200, 100, 100, null));

		when(trainRepository.findBySourceAndDestination("Pune", "Chennai")).thenReturn(trains);

		List<Train> result = trainService.getTrainsBySourceAndDestination("Pune", "Chennai");

		assertEquals(2, result.size());
	}

	@Test
	public void testSaveTrain() {
		Train train = new Train(1L, "A Express", "AC", "Pune", "Chennai", LocalDate.now(), null, null, 100, 50, 50, null);

		when(trainRepository.save(train)).thenReturn(train);

		Train result = trainService.saveTrain(train);

		assertEquals("A Express", result.getTrainName());
	}

	@Test
	public void testUpdateTrain() {
		Train train = new Train(1L, "A Express", "AC", "Pune", "Chennai", LocalDate.now(), null, null, 100, 50, 50, null);

		when(trainRepository.save(train)).thenReturn(train);

		Train result = trainService.updateTrain(train);

		assertEquals("A Express", result.getTrainName());
	}

	@Test
	public void testDeleteTrain() throws TrainException {
		Train train = new Train(1L, "A Express", "AC", "Pune", "Chennai", LocalDate.now(), null, null, 100, 50, 50, null);

		when(trainRepository.findById(1L)).thenReturn(Optional.of(train));

		Train result = trainService.deleteTrain(1L);

		assertEquals("A Express", result.getTrainName());
	}
}