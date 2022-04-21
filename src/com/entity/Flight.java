package com.entity;

import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name="flight")
public class Flight {
	@Id
	@Column(name="flight_id")
	private int id;
	
	@Column(name="number_of_seats")
	private int numberOfSeats;

	@Column(name="price")
	private double price;
	
	@Column(name="source")
	private String source;
	
	@Column(name="destination")
	private String destination;
	
	@Column(name="date_of_departure")
	private String dateOfDeparture;
	
	@Column(name="date_of_arrival")
	private String dateOfArrival;
	
	@ManyToOne
	@JoinColumn(name="airline_id")
	private Airline airline;
	
	@ManyToMany(mappedBy="flights")
	Set<Person> persons;
	
	public Flight() {}

	public Flight(int numberOfSeats, Double price, String source,
			String destination, String dateOfDeparture, String dateOfArrival,
			Airline airline, Set<Person> persons) {
		super();
		this.numberOfSeats = numberOfSeats;
		this.price = price;
		this.source = source;
		this.destination = destination;
		this.dateOfDeparture = dateOfDeparture;
		this.dateOfArrival = dateOfArrival;
		this.airline = airline;
		this.persons = persons;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getNumberOfSeats() {
		return numberOfSeats;
	}

	public void setNumberOfSeats(int numberOfSeats) {
		this.numberOfSeats = numberOfSeats;
	}

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	public String getDestination() {
		return destination;
	}

	public void setDestination(String destination) {
		this.destination = destination;
	}

	public String getDateOfDeparture() {
		return dateOfDeparture;
	}

	public void setDateOfDeparture(String departuredate1) {
		this.dateOfDeparture = departuredate1;
	}

	public String getDateOfArrival() {
		return dateOfArrival;
	}

	public void setDateOfArrival(String dateOfArrival) {
		this.dateOfArrival = dateOfArrival;
	}

	public Airline getAirline() {
		return airline;
	}

	public void setAirline(Airline airline) {
		this.airline = airline;
	}

	public Set<Person> getPersons() {
		return persons;
	}

	public void setPersons(Set<Person> persons) {
		this.persons = persons;
	}
}
