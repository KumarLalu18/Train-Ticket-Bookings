package com.train.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Passenger {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long passengerId;

	@Column(name = "passenger_name")
	private String name;

	private Integer age;

	private String gender;

	@JsonIgnore
	@ManyToOne
	@JoinColumn(name = "bookingId")
	private Booking booking;

	@JsonIgnore
	@ManyToOne
	@JoinColumn(name = "userId")
	private User user;

	@JsonIgnore
	@ManyToOne
	@JoinColumn(name = "trainId")
	private Train train;

}
