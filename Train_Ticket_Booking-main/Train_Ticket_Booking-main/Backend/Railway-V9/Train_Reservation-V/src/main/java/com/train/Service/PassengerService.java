package com.train.Service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;
import org.springframework.stereotype.Service;

import com.train.Repository.PassengerRepository;
import com.train.Repository.UserRepository;
import com.train.model.Passenger;
import com.train.model.User;

@Service
public class PassengerService {

	@Autowired
	private PassengerRepository passengerRepository;

	@Autowired
	private UserRepository userRepository;

	public Passenger savePassenger(Passenger passenger) {
		return passengerRepository.save(passenger);
	}

	public Passenger getPassengerById(Long id) {
		return passengerRepository.findById(id).orElse(null);
	}

	public List<Passenger> getAllPassengers() {
		return (List<Passenger>) passengerRepository.findAll();
	}

	public Passenger updatePassenger(Long id, Passenger passengerDetails) {
		Passenger passenger = passengerRepository.findById(id).orElse(null);
		passenger.setName(passengerDetails.getName());
		passenger.setAge(passengerDetails.getAge());
		return passengerRepository.save(passenger);
	}

	public void deletePassenger(Long id) {
		passengerRepository.deleteById(id);
	}

	public void deleteByUserId(Long userId) throws NotFoundException {
		// Get the booking by ID
		Optional<User> userOptional = userRepository.findById(userId);
		if (!userOptional.isPresent()) {
			throw new NotFoundException();
		}
		User user = userOptional.get();

		List<Passenger> passengers = passengerRepository.findByUser(user);
		passengerRepository.deleteAll(passengers);

	}

	public void deleteAll(List<Passenger> passengers) {
		// TODO Auto-generated method stub
		passengerRepository.deleteAll();

	}

}
