package com.train.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.train.model.Booking;
import com.train.model.Passenger;
import com.train.model.User;

@Repository
public interface PassengerRepository extends JpaRepository<Passenger, Long> {

	List<Passenger> findByBooking(Booking booking);

	void deleteByUserId(Long id);

	List<Passenger> findByUser(User user);

	void save(User user);

	void save(List<Passenger> passengers);

}
