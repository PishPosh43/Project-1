package com.revature.indirectservlet;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class IndirectServlet extends HttpServlet{

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		response.sendRedirect("http://localhost:8080/HelloServlets/dirserv");
		System.out.print("trigger the doGet() method of IndirectServer");
	}

	//purpose: show forward() method
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//Forward() which is fetch from the requestdispatcher which further processes the original request rather than generating a new one
		
		RequestDispatcher rdis = request.getRequestDispatcher("/dirserv");
	}

	
	
	
	
}
