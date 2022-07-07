package com.revature.directservlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.revature.models.User;

public class DirectServlet extends HttpServlet {

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {

		response.setContentType("text/html");

		PrintWriter out = response.getWriter();
		out.println("<html><body><h1> The server is talking directly to the client </h1></body></html>");

	}
	//this method will handle post requests that client sends. We'll instantiate an object and send back the obj's data in the form of a JSON for data interchange
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("application/json");
		
		//instantiate object
		User u = new User(1, "anna", "banana");
		
		//marshall object from pojo to json format and send it to browser in a body of an http response
		//here we're going to transform it AND send it using the PrintWriter
		response.getWriter().write(new ObjectMapper().writeValueAsString(u));
		
		
	}

}
