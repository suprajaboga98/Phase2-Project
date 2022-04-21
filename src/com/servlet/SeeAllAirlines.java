package com.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;

import com.entity.Airline;
import com.entity.Flight;
import com.util.HibernateUtil;

/**
 * Servlet implementation class SeeAllAirlines
 */
@WebServlet("/listairlines")
public class SeeAllAirlines extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public SeeAllAirlines() {
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
			
			Query query = session.createQuery("from Airline");
			List<Airline> airlines = query.list();
			
			if(airlines.size() > 0)
			{
				out.print("<a href=\"dashboard.html\">Dashboard</a> <br/>");
				out.println("<h2>List of Airlines</h2>");
				out.println("<style> table,th,td { border : 1px solid black ; padding :15px;} </style>");
				out.println("<table>");
				out.println("<tr>");
					out.println("<th> Airline Id </th>");
					out.println("<th> Airline </th>");
				out.println("</tr>");
				for(Airline airline : airlines)
				{
					out.println("<tr>");
						out.println("<td>" + airline.getId() + "</td>");
						out.println("<td>" + airline.getAirline() + "</td>");
				    out.println("</tr>");
				}
				out.println("</table>");
			}
			else
			{
				out.println("<html><body>");
				out.println("<h2>Sorry, there are no airlines currently!</h2>");
				out.print("<a href=\"dashboard.html\">Dashboard</a> <br/>");
				out.println("</html></body>");
			}
			session.close();
			out.println("<html><body>");
			out.println("<br/><br/>");
			out.println("<h4><a href=\"addairline.html\">Add Airlines</a></h4>");
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
