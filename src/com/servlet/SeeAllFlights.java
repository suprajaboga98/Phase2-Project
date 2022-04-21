package com.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Date;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.hibernate.Session;
import org.hibernate.SessionFactory;

import com.entity.Flight;
import com.util.HibernateUtil;

/**
 * Servlet implementation class SeeAllFlights
 */
@WebServlet("/seeallflights")
public class SeeAllFlights extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public SeeAllFlights() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        PrintWriter out = response.getWriter();
		
		//open connection
		SessionFactory factory = HibernateUtil.buildSessionFactory();
		Session session = factory.openSession();
		session.beginTransaction();
		
		String query = "Select f from Flight f inner join Airline a " + 
					   "on f.airline=a.id " +
					   "and f.numberOfSeats>0";
		
		List<Flight> flights = session.createQuery(query).list();
		
		if(flights.size() > 0)
		{
			out.print("<a href=\"dashboard.html\">Dashboard</a> <br/>");
			out.println("<h2>List of flights</h2>");
			out.println("<style> table,th,td { border : 1px solid black ; padding :15px;} </style>");
			out.println("<table>");
			out.println("<tr>");
				out.println("<th> Flight Id </th>");
				out.println("<th> Airline </th>");
				out.println("<th> Source </th>");
				out.println("<th> Destination </th>");
				out.println("<th> Departure date </th>");
				out.println("<th> Arrival date </th>");
				out.println("<th> No.of seats available </th>");
				out.println("<th> Price </th>");
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
						out.println("<td><center>" + flight.getNumberOfSeats() + "</center></td>");
						out.println("<td>" + flight.getPrice() + "</td>");
					out.println("</tr>");
				}
			}
			out.println("</table>");
		}
		else
		{
			out.println("<html><body>");
			out.println("<h2>Sorry, there are no flights currently!</h2>");
			out.print("<h4><a href=\"dashboard.html\">Dashboard</a></h4>");
			out.println("</html></body>");
		}
		session.close();
		out.println("<html><body>");
		out.println("<br/><br/>");
		out.println("<h4><a href=\"addflight.jsp\">Add Flights</a></h4>");
		out.println("</html></body>");
		out.close();
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}
}
