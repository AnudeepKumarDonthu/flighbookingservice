package com.flight.booking.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "flights")
public class FlightDetails {

	@Id
	private String FlightNumber;
	@Column(name = "sourcecity")
	private String sourceCity;
	@Column(name = "destinationcity")
	private String desctinationCity;
	@Column(name = "duration")
	private int duration;
	@Column(name = "capacity")
	private int Capacity;

	public String getFlightNumber() {
		return FlightNumber;
	}

	public void setFlightNumber(String flightNumber) {
		FlightNumber = flightNumber;
	}

	public int getCapacity() {
		return Capacity;
	}

	public void setCapacity(int capacity) {
		Capacity = capacity;
	}

	public String getSourceCity() {
		return sourceCity;
	}

	public void setSourceCity(String sourceCity) {
		this.sourceCity = sourceCity;
	}

	public String getDesctinationCity() {
		return desctinationCity;
	}

	public void setDesctinationCity(String desctinationCity) {
		this.desctinationCity = desctinationCity;
	}

	public int getDuration() {
		return duration;
	}

	public void setDuration(int duration) {
		this.duration = duration;
	}

	@Override
	public String toString() {
		return "FlightDetails [FlightNumber=" + FlightNumber + ", Capacity=" + Capacity + ", sourceCity=" + sourceCity
				+ ", desctinationCity=" + desctinationCity + ", duration=" + duration + "]";
	}

}
