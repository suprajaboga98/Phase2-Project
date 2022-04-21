package com.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.hibernate.query.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import com.entity.Flight;
import com.entity.Payment;
import com.entity.Person;
import com.util.HibernateUtil;

/**
 * Servlet implementation class PayOnline
 */
@WebServlet("/payment-details")
public class PayOnline extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public PayOnline() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.sendRedirect("payment.html");
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		PrintWriter out = response.getWriter();
		HttpSession httpsession  = request.getSession();
		
		String personFlightId = (String) httpsession.getAttribute("flightid");
		String personFirstName = (String) httpsession.getAttribute("firstname");
		String personLastName = (String) httpsession.getAttribute("lastname");
		String personEmail = (String) httpsession.getAttribute("email");
		String noofpersons = (String) httpsession.getAttribute("noofpersons");
		
		String name = request.getParameter("name");
		String cardnumber = request.getParameter("cardnumber");
		String expiration = request.getParameter("expiration");
		int cvv = Integer.parseInt(request.getParameter("cvv"));
		
		//open connection
		SessionFactory factory = HibernateUtil.buildSessionFactory();
		Session session = factory.openSession();
		Transaction tx = session.beginTransaction();
		
		int personflightidPrsed = Integer.parseInt(personFlightId);
		
		String query1 = " from Flight where id = " + personflightidPrsed;
		List<Flight> list = session.createQuery(query1).list();
		Set<Flight> flights = new HashSet<Flight>(list);		
		
		Person person = new Person();
		person.setFirstName(personFirstName);
		person.setLastName(personLastName);
		person.setEmail(personEmail);
		person.setFlights(flights);
		
		Payment payment = new Payment();
		payment.setName(name);
		payment.setCardNumber(cardnumber);
		payment.setExpiryDate(expiration);
		payment.setCvv(cvv);
		payment.setPerson(person);
	
		session.save(person);
		session.save(payment);
	  
	    //update no.of seats after the person has booked his flight	
		String query2 = "select numberOfSeats from Flight f" + 
				" where id = " +  personflightidPrsed;
		int result = (int) session.createQuery(query2).getSingleResult();
		int totalSeats = result - Integer.parseInt(noofpersons);
		Query query3 = session.createQuery("update Flight set numberOfSeats=:n where id = :id");
		query3.setParameter("n", totalSeats );
		query3.setParameter("id",personflightidPrsed);
        query3.executeUpdate();
		
		tx.commit();
		session.close();
		response.sendRedirect("confirmation");
		out.close();
	}
}
