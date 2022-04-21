package com.util;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import com.entity.Admin;
import com.entity.Airline;
import com.entity.Flight;
import com.entity.Payment;
import com.entity.Person;

public class HibernateUtil
{
	public static SessionFactory factory = null;
	public static SessionFactory buildSessionFactory()
	{
		factory = new Configuration().configure("hibernate.cfg.xml")
									 .addAnnotatedClass(Admin.class)
									 .addAnnotatedClass(Airline.class)
									 .addAnnotatedClass(Flight.class)
									 .addAnnotatedClass(Payment.class)
									 .addAnnotatedClass(Person.class)
									 .buildSessionFactory();
		return factory;
	}
}


