package com.flight.booking.repository;

import java.util.List;

import javax.transaction.Transactional;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.flight.booking.model.FlightDetails;

@Repository
@Transactional
public class MainRepository {

	@Autowired
	private SessionFactory sessionFactory;

	private Session getSession() {
		return sessionFactory.getCurrentSession();
	}

	public List<FlightDetails> retrieveAllFlights() {
		return retrieveAll();
	}

	public List<FlightDetails> saveFlight(FlightDetails flightDetails) {
		getSession().save(flightDetails);
		return retrieveAll();
	}

	public List<FlightDetails> searchFlights(String sourcecity, String destinationcity) {

		Session session = getSession();

		Criteria criteria = session.createCriteria(FlightDetails.class);
		criteria.add(Restrictions.eq("sourceCity", sourcecity));
		criteria.add(Restrictions.eq("desctinationCity", destinationcity));
		List<FlightDetails> flightList = (List<FlightDetails>) criteria.list();
		return flightList;
	}

	private List<FlightDetails> retrieveAll() {
		return (List<FlightDetails>) getSession().createQuery("from FlightDetails").list();
	}

	public List<FlightDetails> bookFlight(String flightNo) {
		Session session = getSession();

		Criteria criteria = session.createCriteria(FlightDetails.class);
		criteria.add(Restrictions.eq("FlightNumber", flightNo));
		List<FlightDetails> flight = (List<FlightDetails>)criteria.list();
		return flight;
	}

	public void updateCapacity(FlightDetails flight) {
		getSession().update(flight);
		
	}

}
