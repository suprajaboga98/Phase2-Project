package com.entity;

import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name="airline")
public class Airline {
	@Id
	@Column(name="airline_id")
	private int id;
	
	@Column(name="airline")
	private String airline;
	
	@OneToMany(mappedBy="airline")
	private Set<Flight> flights;
	
	public Airline() {}

	public Airline(int id, String airline, Set<Flight> flights) {
		super();
		this.id = id;
		this.airline = airline;
		this.flights = flights;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getAirline() {
		return airline;
	}

	public void setAirline(String airline) {
		this.airline = airline;
	}

	public Set<Flight> getFlights() {
		return flights;
	}

	public void setFlights(Set<Flight> flights) {
		this.flights = flights;
	}
}