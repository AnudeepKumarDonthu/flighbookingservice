/**
 * 
 */
package com.flight.booking.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.flight.booking.model.FlightDetails;
import com.flight.booking.repository.MainRepository;

/**
 * @author Anudeep Kumar
 *
 */
@Service
public class MainService {

	@Autowired
	private MainRepository mainRepository;

	public List<FlightDetails> getAllFlights() {
		return mainRepository.retrieveAllFlights();
	}

	public List<FlightDetails> addFlight(FlightDetails flightDetails) {
		return mainRepository.saveFlight(flightDetails);
	}

	public List<FlightDetails> searchFlights(String sourcecity, String destinationcity) {
		return mainRepository.searchFlights(sourcecity, destinationcity);
	}

	public ResponseEntity<List<FlightDetails>> bookFlight(String flightNo, int capacity) {
		List<FlightDetails> flight = mainRepository.bookFlight(flightNo);
		System.out.println(flight);
		if (flight.get(0).getCapacity() == 0 || flight.get(0).getCapacity() < capacity) {
			return new ResponseEntity<List<FlightDetails>>(flight, HttpStatus.NOT_ACCEPTABLE);
		} else {
			flight.get(0).setCapacity(flight.get(0).getCapacity() - capacity);
			mainRepository.updateCapacity(flight.get(0));
			return new ResponseEntity<List<FlightDetails>>(flight, HttpStatus.OK);
		}
	}

}
