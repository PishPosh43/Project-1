package servlets;

import com.google.gson.Gson;
import controllers.EntityController;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import models.Entity;
import repository.EntitiesSQL;
import util.ResourceNotFoundException;

import java.io.IOException;
import java.sql.SQLException;
import java.util.*;

import static java.lang.System.out;

public class Servlet extends HttpServlet {

    public void init() throws ServletException {

    }

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
