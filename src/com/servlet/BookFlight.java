package com.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.regex.Pattern;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.hibernate.Session;
import org.hibernate.SessionFactory;

import com.entity.Flight;
import com.util.HibernateUtil;

/**
 * Servlet implementation class BookFlight
 */
@WebServlet("/book-flight")
public class BookFlight extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public BookFlight() {
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
		PrintWriter out = response.getWriter();
		
		HttpSession httpsession = request.getSession();
		
		String flightid = request.getParameter("flightid");
		String firstname = request.getParameter("firstname");
		String lastname = request.getParameter("lastname");
		String noofpersons = request.getParameter("noofpersons");
		String email = request.getParameter("email");
		
		httpsession.setAttribute("flightid", flightid);
		httpsession.setAttribute("firstname",firstname);
		httpsession.setAttribute("lastname",lastname);
		httpsession.setAttribute("noofpersons",noofpersons);
		httpsession.setAttribute("email",email);
		
		//open connection
		SessionFactory factory = HibernateUtil.buildSessionFactory();
		Session session = factory.openSession();
		session.beginTransaction();
		
		String query = "Select f from Flight f inner join Airline a " + 
				   "on f.airline=a.id " +
				   "and f.numberOfSeats>0";
		
		if(flightid != null)
		{
			int flightIdParsed = Integer.parseInt(flightid);
			String FlightIdQuery = " and f.id= " + flightIdParsed;
			query = query.concat(FlightIdQuery);
		}
		
		List<Flight> flights = session.createQuery(query).list();
		
		out.println("<html><body>");
		if(flights.size()<=0)
		{
			out.println("<a href=\"seeallflights\">Re-Enter your information</a><br/><br/>");
			out.println("<h2>Enter a valid flight id</h2>");
			return;
		}
		out.println("<h4><a href=\"seeallflights\">Back</a></h4>");
		out.println("<h2>Confirm flight details</h2>");
		for(Flight flight : flights)
		{
			if(flight.getNumberOfSeats() > 0)
			{
				out.println("Flight ID: " + flight.getId() + "<br/>");
				out.println("Airline: " + flight.getAirline().getAirline().toString() + "<br/>");
				out.println("Source: " + flight.getSource() + "<br/>");
				out.println("Destination: " + flight.getDestination() + "<br/>");
				out.println("Departure date: " + flight.getDateOfDeparture() + "<br/>");
				out.println("Arrival date: " + flight.getDateOfArrival() + "<br/>");
				out.println("No.of Persons: " + noofpersons + "<br/>");
				out.println("Total Price: " + flight.getPrice() * Double.parseDouble(noofpersons) + "<br/>");				
			}
		}
		request.getRequestDispatcher("payment.html").include(request,response);
		out.println("</hmtl></body>");
		
		session.close();
		out.close();
	}
}
