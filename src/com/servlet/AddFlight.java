package com.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import com.entity.Airline;
import com.entity.Flight;
import com.util.HibernateUtil;

/**
 * Servlet implementation class AddFlight
 */
@WebServlet("/add-flight")
public class AddFlight extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AddFlight() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		SessionFactory factory = HibernateUtil.buildSessionFactory();
		Session session = factory.openSession();
		
		PrintWriter out = response.getWriter();
        Airline airline = new Airline();
		
        int id = Integer.parseInt(request.getParameter("flightid"));
        String airlinename = request.getParameter("airlinename");
        int noofseats = Integer.parseInt(request.getParameter("noofseats"));
        String source = request.getParameter("source");
        String destination = request.getParameter("destination");
		String departuredate = request.getParameter("departuredate");
		String arrivaldate = request.getParameter("arrivaldate");
		Double price = Double.parseDouble(request.getParameter("price"));
		
		String query2 = "from Airline where airline = '" + airlinename + "'";
		airline = (Airline) session.createQuery(query2).getSingleResult();
		
		Flight flight = new Flight();
		flight.setId(id);
		flight.setAirline(airline);
		flight.setNumberOfSeats(noofseats);
		flight.setSource(source);
		flight.setDestination(destination);
		flight.setDateOfDeparture(departuredate);
		flight.setDateOfArrival(arrivaldate);
		flight.setPrice(price);
		
		Transaction tx = session.beginTransaction();
		session.save(flight);
		tx.commit();
		session.close();
		
		out.println("<html><body>");
		out.println("<h2>Flight added Succesfully</h2>");
		out.println("<br/><br/><h4><a href=\"seeallflights\">Back</a></h4>");
		out.println("</html></body>");
	}
}
