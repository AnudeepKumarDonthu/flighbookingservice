package com.flight.booking.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.flight.booking.main.Application;
import com.flight.booking.model.Booking;
import com.flight.booking.model.FlightDetails;
import com.flight.booking.service.MainService;

import io.swagger.annotations.Api;

@Controller
@ControllerAdvice
@RequestMapping(value = "/flight")
@Api(value = "Flight Ticket Booking")
public class BookingController {
	
	@Autowired
	private MainService mainService;

	@MessageMapping("/bookticket")
	@SendTo("/update/availability")
	public ResponseEntity<List<FlightDetails>> booking(@RequestBody Booking booking) {
		List<FlightDetails> selectedflight = new ArrayList<FlightDetails>();
		if (booking.getFlightNumber() != null && booking.getNoOfTickets() != 0) {
			return mainService.bookFlight(booking.getFlightNumber(),booking.getNoOfTickets());
		} else {
			return new ResponseEntity<List<FlightDetails>>(selectedflight, HttpStatus.BAD_REQUEST);
		}
	}
}
