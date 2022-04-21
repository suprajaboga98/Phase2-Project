package com.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

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
 * Servlet implementation class Confirmation
 */
@WebServlet("/confirmation")
public class Confirmation extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Confirmation() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		PrintWriter out = response.getWriter();
		
		HttpSession httpsession = request.getSession(false);
		
		String flightid = (String) httpsession.getAttribute("flightid");
		String firstname = (String)httpsession.getAttribute("firstname");
		String lastname = (String)httpsession.getAttribute("lastname");
		String email = (String)httpsession.getAttribute("email");
		String noofpersons = (String) httpsession.getAttribute("noofpersons");
		int flightIdConverted = Integer.parseInt(flightid);
		
		//open connection
		SessionFactory factory = HibernateUtil.buildSessionFactory();
		Session session = factory.openSession();
		session.beginTransaction();
		
		String query = "Select f from Flight f inner join Airline a " +
					   "on f.airline=a.id " + 
					   "and f.numberOfSeats>0 " + 
					   "and f.id= " + flightIdConverted;
		
		List<Flight> flights = session.createQuery(query).list();
		
		out.print("<a href=\"dashboard.html\">Dashboard</a> <br/>");
		out.println("<h2>Your payment is successful</style></h2>");
		out.println("<h2>Here are your details, " + firstname + "!</h2>");
		out.println("<style> table,th,td { border : 1px solid black ; padding :15px;} </style>");
		out.println("<table>");
		out.println("<tr>");
			out.println("<th> First Name </th>");
			out.println("<th> Last Name </th>");
			out.println("<th> Email </th>");
		out.println("</tr>");
		out.println("<tr>");
			out.println("<td>" + firstname + "</td>");
			out.println("<td>" + lastname + "</td>");
			out.println("<td>" + email + "</td>");
	    out.println("</tr>");
	    out.println("</table>");
	    out.println("<br/><br/>");
		
		if(flights.size() > 0)
		{
			out.println("<style> table,th,td { border : 1px solid black ; padding :15px;} </style>");
			out.println("<table>");
			out.println("<tr>");
				out.println("<th> Flight Id </th>");
				out.println("<th> Airline </th>");
				out.println("<th> Source </th>");
				out.println("<th> Destination </th>");
				out.println("<th> Departure date </th>");
				out.println("<th> Arrival date </th>");
				out.println("<th> No.of Seats </th>");
				out.println("<th> Total Amount Paid </th>");
			out.println("</tr>");
			for(Flight flight : flights)
			{
				if(flight.getNumberOfSeats() > 0)
				{
					out.println("<tr>");
						out.println("<td>" + flight.getId() + "</td>");
						out.println("<td>" + flight.getAirline().getAirline().toString() + "</td>");
						out.println("<td>" + flight.getSource() + "</td>");
						out.println("<td>" + flight.getDestination() + "</td>");
						out.println("<td>" + flight.getDateOfDeparture() + "</td>");
						out.println("<td>" + flight.getDateOfArrival() + "</td>");
						out.println("<td>" + noofpersons + "</td>");
						out.println("<td>" + flight.getPrice() * Double.parseDouble(noofpersons) + "</td>");
					out.println("</tr>");
				}
			}
			out.println("</table>");
		}
		else
		{
			out.println("<html><body>");
			out.println("<h2>You don't have any flights currently, Sorry!</h2>");
			out.print("<h4><a href=\"index.html\">Home</a></h4>");
			out.println("</html></body>");
		}
		session.close();
		out.close();
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}
}
