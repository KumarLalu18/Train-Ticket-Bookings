package com.train.model;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Booking {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long bookingId;


	@ManyToOne
	@JoinColumn(name = "trainId")
	private Train train;

	private Integer totalTickets;

	private Integer totalFare;

	@OneToMany(cascade = CascadeType.ALL, mappedBy = "booking")
	private List<PassengerDetails> passengerDetails = new ArrayList<>();

	@JsonIgnore
	@ManyToOne
	@JoinColumn(name = "userId")
	private User user;
}
