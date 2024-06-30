package com.train.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.train.model.Passenger;
import com.train.model.Ticket;
import com.train.model.Train;
import com.train.model.User;

@Repository
public interface TicketRepository extends JpaRepository<Ticket, Long> {

	void save(Passenger passenger);

	void save(Train train);

	void save(User user);

	List<Ticket> findByBooking_BookingId(Long bookingId);

	List<Ticket> findByBookingTrainTrainId(Long trainId);

}
