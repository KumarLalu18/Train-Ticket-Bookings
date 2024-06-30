package com.train.Service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;
import org.springframework.stereotype.Service;

import com.train.Exception.ThrowValidException;
import com.train.Repository.BookingRepository;
import com.train.Repository.PassengerRepository;
import com.train.Repository.TrainRepository;
import com.train.Repository.UserRepository;
import com.train.model.Booking;
import com.train.model.Passenger;
import com.train.model.Train;
import com.train.model.User;

@Service
public class BookingService {

	@Autowired
	private BookingRepository bookingRepository;

	@Autowired
	private PassengerRepository passengerRepository;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private TrainRepository trainRepository;

	public List<Booking> getAllBookings() {
		return (List<Booking>) bookingRepository.findAll();
	}

	public List<Passenger> findPassengersByBookingId(Long bookingId) throws NotFoundException {
		Optional<Booking> bookingOptional = bookingRepository.findById(bookingId);
		if (bookingOptional.isPresent()) {
			Booking booking = bookingOptional.get();
			return passengerRepository.findByBooking(booking);
		} else {
			throw new NotFoundException();
		}
	}

	public Booking retrieveBookingById(Long bookingId) {
		try {
			Booking booking = bookingRepository.findById(bookingId).get();
			List<Passenger> passengers = passengerRepository.findByBooking(booking);
			return booking;
		} catch (Exception e) {
			throw new ThrowValidException("Wrong Booking id:" + bookingId);
		}
	}

//	@Transactional
	public Booking createBooking(Long trainId, Long userId, List<Passenger> passengers) throws NotFoundException {

		// Get the train and user objects from the database
		Train train = trainRepository.findById(trainId).orElseThrow(() -> new RuntimeException("Train not found"));
		User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));

		// Create a new booking object
		Booking booking = new Booking();
		booking.setUser(user);
		booking.setTrain(train);

		// Calculate the total fare
		int totalFare = passengers.size() * train.getFare();
		booking.setTotalFare(totalFare);

		Integer totalSeats = passengers.size();
		booking.setTotalTickets(totalSeats);

		booking = bookingRepository.save(booking);

		// Set the booking ID for each passenger
		for (Passenger passenger : passengers) {
			passenger.setBooking(booking);
			passenger.setUser(user);
			passenger.setTrain(train);
			passengerRepository.save(passenger);

		}
		List<Passenger> passengers1 = passengerRepository.findByBooking(booking);
		if(passengers1.size()==0)
		{
			throw new ThrowValidException("Add passengers to Book Ticket");
		}

		return booking;
	}

	public void deleteBookingById(Long bookingId) {
		Booking booking = bookingRepository.findById(bookingId)
				.orElseThrow(() -> new RuntimeException("Booking not found"));

		List<Passenger> passengers = passengerRepository.findByBooking(booking);
		Train train = booking.getTrain();
		train.setAvailableSeats(train.getAvailableSeats() + booking.getTotalTickets());
		trainRepository.save(train);

		bookingRepository.delete(booking);
	}

	public void deleteAllBookings() {
		bookingRepository.deleteAll();
	}

	public List<Booking> retrieveBookingUserById(Long userId) {
		// TODO Auto-generated method stub
		Optional<User> user = userRepository.findById(userId);
		return bookingRepository.findByUser(user.get());

	}

}