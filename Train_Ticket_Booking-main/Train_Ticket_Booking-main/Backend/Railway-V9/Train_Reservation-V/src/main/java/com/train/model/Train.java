package com.train.model;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Train {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long trainId;
	private String trainName;
	private String trainType;
	private String source;
	private String destination;
	private LocalDate journeyDate;
	private LocalTime arrivalTime;
	private LocalTime departureTime;
	private Integer fare;
	private Integer availableSeats;
	private Integer totalSeats;

	@JsonIgnore
	@OneToMany(mappedBy = "train", cascade = CascadeType.ALL)
	private Set<Booking> booking;
}
