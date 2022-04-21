package com.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import com.util.HibernateUtil;

/**
 * Servlet implementation class ChangePassword
 */
@WebServlet("/change-password")
public class ChangePassword extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ChangePassword() {
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
        
        SessionFactory factory = HibernateUtil.buildSessionFactory();
		Session session = factory.openSession();
		Transaction tx = session.beginTransaction();
		
		String newpassword = request.getParameter("newpassword");
		String confirmpassword = request.getParameter("confirmpassword");
		
		int adminId = (int) httpsession.getAttribute("adminid");
		
		if(newpassword.equals(confirmpassword))
		{
			Query query2 = session.createQuery("update Admin set password=:p where id = :id");
			query2.setParameter("p", newpassword );
			query2.setParameter("id", adminId );
	        query2.executeUpdate();
	        out.println("<html><body>");
			out.println("<h2>Your password is changed successfully!!</h2>");
			out.println("<h4><a href=\"dashboard.html\">Dashboard</a></h4>");
			out.println("</html></body>");
		}
		else
		{
			out.println("<html><body>");
			out.println("<h2>Your password is not matching, please check again!!</h2>");
			out.println("<h4><a href=\"changepassword.html\">Back</a></h4>");
			out.println("</html></body>");
		}
	
		tx.commit();
		session.close();
	}
}
