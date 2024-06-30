package com.train.Controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.train.Repository.PassengerRepository;
import com.train.Repository.UserRepository;
import com.train.Service.PassengerService;
import com.train.model.Passenger;
import com.train.model.User;

@RestController
@RequestMapping("/passengers")
@CrossOrigin(origins = "*")
public class PassengerController {

	@Autowired
	private PassengerService passengerService;

	@Autowired
	private PassengerRepository passengerRepository;

	@Autowired
	private UserRepository userRepository;

	@PostMapping
	public Passenger createPassenger(@RequestBody Passenger passenger) {
		return passengerService.savePassenger(passenger);
	}

	@GetMapping("/{id}")
	public Passenger getPassengerById(@PathVariable Long id) {
		return passengerService.getPassengerById(id);
	}

	@GetMapping
	public List<Passenger> getAllPassengers() {
		return passengerService.getAllPassengers();
	}

	@PutMapping("/{id}")
	public Passenger updatePassenger(@PathVariable Long id, @RequestBody Passenger passengerDetails) {
		return passengerService.updatePassenger(id, passengerDetails);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<?> deletePassenger(@PathVariable Long id) {
		passengerService.deletePassenger(id);
		return ResponseEntity.ok().build();
	}

	@DeleteMapping("/user/{userId}")
	public void deleteByUserId(@PathVariable Long userId) throws NotFoundException {
		
		Optional<User> userOptional = userRepository.findById(userId);
		if (!userOptional.isPresent()) {
			throw new NotFoundException();
		}
		User user = userOptional.get();

		// Delete all passengers associated with the booking
		List<Passenger> passengers = passengerRepository.findByUser(user);
		passengerService.deleteAll(passengers);

	}
}
