package com.train.Service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.train.Exception.ThrowValidException;
import com.train.Repository.BookingRepository;
import com.train.Repository.PassengerDetailsRepository;
import com.train.Repository.PassengerRepository;
import com.train.Repository.TicketRepository;
import com.train.Repository.TrainRepository;
import com.train.model.Booking;
import com.train.model.Passenger;
import com.train.model.PassengerDetails;
import com.train.model.Ticket;
import com.train.model.Train;

@Service
public class TicketService {

	@Autowired
	private TicketRepository ticketRepository;

	@Autowired
	private BookingRepository bookingRepository;

	@Autowired
	private PassengerRepository passengerRepository;

	@Autowired
	private PassengerDetailsRepository passengerDetailsRepository;

	@Autowired
	private TrainRepository trainRepository;

	public List<Ticket> getAllTickets() {

		return ticketRepository.findAll();
	}

	public List<Booking> findByUserIdAndBookingId(Long userId, Long bookingId) {
		return bookingRepository.findByUserIdAndBookingId(userId, bookingId);

	}
	public List<Ticket> getTicketsByTrainId(Long trainId) {
        return ticketRepository.findByBookingTrainTrainId(trainId);
    }
	
	

	public List<Booking> findByUserId(Long userId) {
		return bookingRepository.findByUserId(userId);

	}

	
	

	public Ticket createTicket(Long bookingId) {
		Booking booking = bookingRepository.findById(bookingId)
				.orElseThrow(() -> new RuntimeException("Booking not found"));

		List<Ticket> tick = ticketRepository.findByBooking_BookingId(bookingId);
		for (Ticket t : tick) {
			if (t.getBooking().getBookingId() == bookingId) {
				throw new ThrowValidException("Booking Is Already Exists");
			}
		}
		Train train = booking.getTrain();

		if (passengerRepository.findByBooking(booking).size() > train.getAvailableSeats()) {
			throw new RuntimeException(
					"No enough seats available. Only " + train.getAvailableSeats() + " seats are available.");
		} else {
			
			Ticket ticket = new Ticket();
			ticket.setBooking(booking);
			ticket.setStatus("Reserved");

			ticket = ticketRepository.save(ticket);

			List<Passenger> passengers = passengerRepository.findByBooking(booking);

			// Adjust the available tickets for the train
			train.setAvailableSeats(train.getAvailableSeats() - passengers.size());
			trainRepository.save(train);

			for (Passenger passenger : passengers) {

				PassengerDetails details = new PassengerDetails();
				details.setBooking(booking);
				details.setAge(passenger.getAge());
				details.setName(passenger.getName());
				details.setGender(passenger.getGender());
				passengerDetailsRepository.save(details);

				passengerRepository.delete(passenger);
			}

			return ticket;
		}
	}

	public void deleteTicketByBookingId(Long bookingId) {
		List<Ticket> tickets = ticketRepository.findByBooking_BookingId(bookingId);

		Booking booking = bookingRepository.findById(bookingId)
				.orElseThrow(() -> new RuntimeException("Booking not found"));

		Train train = booking.getTrain();

		train.setAvailableSeats(train.getAvailableSeats() + booking.getTotalTickets());

		if (train.getAvailableSeats() > train.getTotalSeats()) {
			train.setAvailableSeats(train.getTotalSeats());
		}

		trainRepository.save(train);
		for (Ticket ticket : tickets) {
			ticketRepository.delete(ticket);
		}

	}

}
