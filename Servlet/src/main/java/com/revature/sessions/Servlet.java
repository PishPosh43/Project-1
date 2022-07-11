package com.revature.sessions;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.fasterxml.jackson.databind.ObjectMapper;

public class Servlet extends javax.servlet.http.HttpServlet{
	 public void init() throws ServletException {

	    }
	 
	 ///////////////////Need to create RequestManager Class

	    @Override
	    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
	        RequestManager.getProcess(req, resp);
	    }


	    @Override
	    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
	        RequestManager.postProcess(req, resp);

	    }


	    @Override
	    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
	        RequestManager.putProcess(req,resp);

	    }


	    @Override
	    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
	     RequestManager.deleteProcess(req,resp);
	    }

}
