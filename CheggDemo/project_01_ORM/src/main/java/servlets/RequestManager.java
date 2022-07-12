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

public class RequestManager extends HttpServlet {

    public static Gson gson = new Gson();
    public static EntityController ec = new EntityController();
    Entity entity;

    public void init() throws ServletException {

    }


    public static void getProcess(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // Uses URL Rewriting to keep track of session
    	
    	String uri = req.getRequestURI();
        String[] uriTokens = uri.split("/");
        System.out.println(Arrays.toString(uriTokens));
        out.println("URI Tokens Number: " + uriTokens.length);
        resp.getWriter().println("URI Tokens Number: " + uriTokens.length);
        resp.getWriter().println(Arrays.toString(uriTokens));

        switch (uriTokens.length) {
            case 0:
            case 1:
            case 2: {
                resp.sendError(400);
                break;
            }
            case 3: {
                req.setAttribute("table", uriTokens[2]);
                ec.getEntityAllEntities(req, resp);
                break;


            }
            case 4: {
                req.setAttribute("table", uriTokens[2]);
                req.setAttribute("id", uriTokens[3]);
                try {
                    ec.getEntityById(req, resp);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                break;
            }
            case 5: {
                req.setAttribute("table", uriTokens[2]);
                req.setAttribute("column", uriTokens[3]);
                req.setAttribute("target", uriTokens[4]);
                try {
                    ec.getEntityByColumn(req, resp);
                } catch (SQLException | ResourceNotFoundException e) {
                    e.printStackTrace();
                }
                break;
            }

            default: {
                resp.sendError(400);
            }
        }


    }


    public static void postProcess(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String uri = req.getRequestURI();
        String[] uriTokens = uri.split("/");
        System.out.println(Arrays.toString(uriTokens));
        out.println("URI Tokens Number: " + uriTokens.length);
        // resp.getWriter().println("URI Tokens Number: " + uriTokens.length);
        //resp.getWriter().println(Arrays.toString(uriTokens));

        if (uriTokens.length <= 3) {
            resp.sendError(400);
        } else {
            req.setAttribute("table", uriTokens[2]);
            LinkedList data = new LinkedList();
            for (int i = 3; i < uriTokens.length; i++) {
                data.add(uriTokens[i]);
            }
            req.setAttribute("data", data);
            try {
                ec.addEntity(req, resp);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }


    }


    public static void putProcess(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String uri = req.getRequestURI();
        String[] uriTokens = uri.split("/");
        System.out.println(Arrays.toString(uriTokens));
        out.println("URI Tokens Number: " + uriTokens.length);
        // resp.getWriter().println("URI Tokens Number: " + uriTokens.length);
        //resp.getWriter().println(Arrays.toString(uriTokens));

        if (uriTokens.length <= 4) {
            resp.sendError(400);
        } else {
            req.setAttribute("table", uriTokens[2]);
            LinkedList data = new LinkedList();
            for (int i = 3; i < uriTokens.length; i++) {
                data.add(uriTokens[i]);
            }
            req.setAttribute("data", data);
            ec.updateEntity(req, resp);
        }

    }


    protected static void deleteProcess(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String uri = req.getRequestURI();
        System.out.println(uri);

        String[] uriTokens = uri.split("/");
        System.out.println(Arrays.toString(uriTokens));

        switch (uriTokens.length) {
            //if the uriTokens only has two elements, a blank element and the project name, then nothing to process.
            case 0:
            case 1:
            case 2:
            case 3: {
                resp.sendError(404);
                break;

            }

            //if the uriTokens is exactly 3 then it also has the collection name, but no path parameter.
            case 4: {
                req.setAttribute("table", uriTokens[2]);
                req.setAttribute("id", uriTokens[3]);
                try {
                    ec.deleteEntityById(req, resp);
                } catch (ResourceNotFoundException e) {
                    e.printStackTrace();
                }

                break;
            }
            case 5: {
                req.setAttribute("table", uriTokens[2]);
                req.setAttribute("column", uriTokens[3]);
                req.setAttribute("target", uriTokens[4]);
                try {
                    ec.deleteEntityByColumn(req, resp);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                break;
            }


            default: {
                resp.sendError(400);
                break;
            }

        }
    }


}
