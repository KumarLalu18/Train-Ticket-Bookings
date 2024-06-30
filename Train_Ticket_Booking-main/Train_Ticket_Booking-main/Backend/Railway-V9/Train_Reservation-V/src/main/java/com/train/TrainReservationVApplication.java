package com.train;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EntityScan(basePackages="com.train.model")
@EnableJpaRepositories(basePackages = "com.train.Repository")
public class TrainReservationVApplication {

	public static void main(String[] args) {
		SpringApplication.run(TrainReservationVApplication.class, args);
	}

}
