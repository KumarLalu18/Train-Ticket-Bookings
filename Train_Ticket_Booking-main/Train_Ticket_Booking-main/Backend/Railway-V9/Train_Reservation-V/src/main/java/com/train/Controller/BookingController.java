package com.train.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.train.Service.BookingService;
import com.train.Service.PassengerService;
import com.train.model.Booking;
import com.train.model.Passenger;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/bookings")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class BookingController {

	@Autowired
	private BookingService bookingService;
	
	@Autowired
	private PassengerService passengerService;

	@PostMapping("/{trainId}/{userId}")
	public ResponseEntity<Booking> createBooking(@PathVariable Long trainId, @PathVariable Long userId,
			@RequestBody List<Passenger> passengers) throws NotFoundException {
		//id=userid
		Booking booking = bookingService.createBooking(trainId, userId, passengers);

		
		return ResponseEntity.ok(booking);
	}

	@GetMapping
	public List<Booking> getAllBookings() {
		return bookingService.getAllBookings();
	}

	@GetMapping("/{bookingId}")
	public ResponseEntity<Booking> retrieveBookingById(@PathVariable Long bookingId) {
		// Retrieve the booking by ID
		Booking booking = bookingService.retrieveBookingById(bookingId);

		// Return the booking
		return ResponseEntity.ok(booking);
	}

	@GetMapping("/getByUserId/{userId}")
	public ResponseEntity<List<Booking>> retrieveBookingByUserId(@PathVariable Long userId) {
		
		List<Booking> booking = bookingService.retrieveBookingUserById(userId);

	
		return ResponseEntity.ok(booking);
	}

	@GetMapping("/{bookingId}/passengers")
	public ResponseEntity<List<Passenger>> findPassengersByBookingId(@PathVariable Long bookingId) throws NotFoundException
			 {
		
		List<Passenger> passengers = bookingService.findPassengersByBookingId(bookingId);

		
		return ResponseEntity.ok(passengers);
	}

	@DeleteMapping("/{bookingId}")
	public void deleteBookingById(@PathVariable Long bookingId) {

		bookingService.deleteBookingById(bookingId);
	}

	
	@DeleteMapping("/all")
	public void deleteAllBookings() {
		bookingService.deleteAllBookings();
	}
	

	@DeleteMapping("/deletePassById/{id}")
	public ResponseEntity<?> deletePassenger(@PathVariable Long id) {
		passengerService.deletePassenger(id);
		return ResponseEntity.ok().build();
	}
}