package com.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import com.entity.Airline;
import com.util.HibernateUtil;

/**
 * Servlet implementation class AddAirline
 */
@WebServlet("/add-airline")
public class AddAirline extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AddAirline() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.getRequestDispatcher("addairline.html").forward(request,response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        PrintWriter out = response.getWriter();
		
        int id = Integer.parseInt(request.getParameter("airlineid"));
		String name = request.getParameter("airline");
		
		Airline a = new Airline();
		a.setId(id);
		a.setAirline(name);;
		
		SessionFactory factory = HibernateUtil.buildSessionFactory();
		Session session = factory.openSession();
		
		Transaction tx = session.beginTransaction();
		session.save(a);
		tx.commit();
		session.close();
		
		out.println("<html><body>");
		out.println("Airline added Succesfully");
		out.println("<br/><br/><h4><a href=\"listairlines\">Back</a></h4>");
		out.println("</html></body>");
	}
}
