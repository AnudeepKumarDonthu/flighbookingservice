package com.flight.booking.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.flight.booking.model.FlightDetails;
import com.flight.booking.service.MainService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@RequestMapping(value = "/flight")
@ControllerAdvice
@Api(value = "Flight Booking")
public class MainContoller {

	Logger LOGGER = LoggerFactory.getLogger(MainContoller.class);

	@Autowired
	private MainService mainService;

	@ApiOperation(value = "Check for Application Running status", response = void.class)
	@RequestMapping(value = "/health", method = RequestMethod.GET, produces = MediaType.TEXT_PLAIN_VALUE)
	public ResponseEntity<String> health() {
		return new ResponseEntity<String>("I am healty", HttpStatus.OK);
	}

	@ApiOperation(value = "Get All Flight Details")
	@ApiResponses(value = {
			@ApiResponse(code = 200, response = FlightDetails.class, message = "Successful Return of all flight details") })
	@RequestMapping(value = "/getallflights", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<FlightDetails>> booking() {
		return new ResponseEntity<List<FlightDetails>>(mainService.getAllFlights(), HttpStatus.OK);
	}

	@ApiOperation(value = "Add new Flight")
	@ApiResponses(value = {
			@ApiResponse(code = 200, response = FlightDetails.class, message = "Successful add of new flight details"),
			@ApiResponse(code = 400, response = FlightDetails.class, message = "Invalid flight details Request") })
	@RequestMapping(value = "/addflight", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<FlightDetails>> addNewFlight(@RequestBody FlightDetails flightDetails) {
		List<FlightDetails> flights = new ArrayList<FlightDetails>();
		if (flightDetails.getFlightNumber() != null && flightDetails.getFlightNumber() != ""
				&& flightDetails.getSourceCity() != null && flightDetails.getSourceCity() != ""
				&& flightDetails.getDesctinationCity() != null && flightDetails.getDesctinationCity() != ""
				&& flightDetails.getCapacity() != 0 && flightDetails.getDuration() != 0) {
			flights = mainService.addFlight(flightDetails);
			return new ResponseEntity<List<FlightDetails>>(flights, HttpStatus.OK);
		} else {
			return new ResponseEntity<List<FlightDetails>>(flights, HttpStatus.BAD_REQUEST);
		}
	}

	@ApiOperation(value = "Search Flight Existing Flights")
	@ApiResponses(value = {
			@ApiResponse(code = 200, response = FlightDetails.class, message = "Successful return of flight details"),
			@ApiResponse(code = 404, response = FlightDetails.class, message = "No Flights Found"),
			@ApiResponse(code = 400, response = FlightDetails.class, message = "Bad Request") })
	@RequestMapping(value = "/searchflights", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<FlightDetails>> searchFlights(@RequestBody Map<Object, Object> searchData) {

		List<FlightDetails> availableFlights = new ArrayList<FlightDetails>();

		if (searchData.get("sourcecity") != null && searchData.get("destinationcity") != null) {
			availableFlights = mainService.searchFlights((String) searchData.get("sourcecity"),
					(String) searchData.get("destinationcity"));

			if (availableFlights.isEmpty()) {
				return new ResponseEntity<List<FlightDetails>>(availableFlights, HttpStatus.NOT_FOUND);
			}
			return new ResponseEntity<List<FlightDetails>>(availableFlights, HttpStatus.OK);
		} else {
			return new ResponseEntity<List<FlightDetails>>(availableFlights, HttpStatus.BAD_REQUEST);
		}
	}

	@ExceptionHandler(value = HttpMessageNotReadableException.class)
	public final ResponseEntity<String> handleException(
			HttpMessageNotReadableException httpMessageNotReadableException) {
		LOGGER.error("[handleException] :: Method start");
		HttpHeaders headers = new HttpHeaders();
		headers.add("content-type", "application/json");
		LOGGER.error("Exception occured while handling the Request");
		LOGGER.error("[handleException] :: Method End");
		return new ResponseEntity<String>("Exception Occurred", headers, HttpStatus.BAD_REQUEST);
	}

}
