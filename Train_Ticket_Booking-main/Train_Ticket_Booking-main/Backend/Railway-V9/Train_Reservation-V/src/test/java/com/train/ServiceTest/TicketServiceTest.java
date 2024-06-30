package com.train.ServiceTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.train.Repository.BookingRepository;
import com.train.Repository.PassengerDetailsRepository;
import com.train.Repository.PassengerRepository;
import com.train.Repository.TicketRepository;
import com.train.Repository.TrainRepository;
import com.train.Service.TicketService;
import com.train.model.Booking;
import com.train.model.Passenger;
import com.train.model.PassengerDetails;
import com.train.model.Ticket;
import com.train.model.Train;

class TicketServiceTest {

	@Mock
	private TicketRepository ticketRepository;

	@Mock
	private BookingRepository bookingRepository;

	@Mock
	private PassengerRepository passengerRepository;

	@Mock
	private PassengerDetailsRepository passengerDetailsRepository;

	@Mock
	private TrainRepository trainRepository;

	@InjectMocks
	private TicketService ticketService;

	@BeforeEach
	void setUp() {
		MockitoAnnotations.openMocks(this);
	}

	@Test
	void getAllTickets_Success() {
		List<Ticket> tickets = new ArrayList<>();
		tickets.add(new Ticket());
		tickets.add(new Ticket());
		when(ticketRepository.findAll()).thenReturn(tickets);

		List<Ticket> result = ticketService.getAllTickets();

		assertEquals(2, result.size());
		verify(ticketRepository, times(1)).findAll();
	}

	@Test
	void findByUserIdAndBookingId_Success() {
		List<Booking> bookings = new ArrayList<>();
		Booking booking = new Booking();
		booking.setBookingId(1L);
		bookings.add(booking);
		when(bookingRepository.findByUserIdAndBookingId(1L, 1L)).thenReturn(bookings);

		List<Booking> result = ticketService.findByUserIdAndBookingId(1L, 1L);

		assertEquals(1, result.size());
		verify(bookingRepository, times(1)).findByUserIdAndBookingId(1L, 1L);
	}

 	@Test
	void createTicket_Success() {
		Booking booking = new Booking();
		booking.setBookingId(1L);
		Train train = new Train();
		train.setTrainId(1L);
		train.setAvailableSeats(10);
		booking.setTrain(train);
		when(bookingRepository.findById(1L)).thenReturn(Optional.of(booking));

		Passenger passenger = new Passenger();
		passenger.setBooking(booking);
		passenger.setAge(30);
		passenger.setName("John Doe");
		passenger.setGender("Male");
		List<Passenger> passengers = new ArrayList<>();
		passengers.add(passenger);
		when(passengerRepository.findByBooking(booking)).thenReturn(passengers);

		PassengerDetails passengerDetails = new PassengerDetails();
		passengerDetails.setBooking(booking);
		passengerDetails.setAge(30);
		passengerDetails.setName("John Doe");
		passengerDetails.setGender("Male");
		when(passengerDetailsRepository.save(any(PassengerDetails.class))).thenReturn(passengerDetails);

		doNothing().when(passengerRepository).delete(passenger);

		Ticket ticket = new Ticket();
		ticket.setBooking(booking);
		ticket.setStatus("Reserved");
		when(ticketRepository.save(any(Ticket.class))).thenReturn(ticket);

		train.setAvailableSeats(10 - passengers.size());
		when(trainRepository.save(train)).thenReturn(train);

		Ticket result = ticketService.createTicket(1L);

		assertEquals(1L, result.getBooking().getBookingId());
		assertEquals("Reserved", result.getStatus());

		verify(passengerRepository, times(1)).delete(passenger);
		verify(passengerDetailsRepository, times(1)).save(any(PassengerDetails.class));
		verify(ticketRepository, times(1)).save(any(Ticket.class));
		verify(trainRepository, times(1)).save(train);
	}

}
