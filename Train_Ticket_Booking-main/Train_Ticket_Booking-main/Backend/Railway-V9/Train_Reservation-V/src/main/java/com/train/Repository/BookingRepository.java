package com.train.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.train.model.Booking;
import com.train.model.User;

@Repository
public interface BookingRepository extends JpaRepository<Booking, Long> {

	List<Booking> findByUser(User user);

	List<Booking> findByUserIdAndBookingId(Long userId, Long bookingId);

	List<Booking> findByUserId(Long userId);

	

}