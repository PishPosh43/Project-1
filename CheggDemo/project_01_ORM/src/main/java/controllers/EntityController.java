package controllers;

import com.google.gson.Gson;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import models.Entities;
import models.Entity;
import repository.EntitiesSQL;
import repository.Row;
import services.EntityServices;
import util.ResourceNotFoundException;

import java.io.BufferedReader;
import java.io.IOException;
import java.sql.SQLException;
import java.util.LinkedList;

import static repository.EntitiesSQL.selectByPrimary;

public class EntityController {

    static Gson gson = new Gson();

     static EntityServices es = new EntityServices();

    public void getEntityAllEntities(HttpServletRequest request, HttpServletResponse response) throws IOException {
        EntityServices.populate(request, response);
    }

    public void getEntityById(HttpServletRequest request, HttpServletResponse response) throws IOException, SQLException {
        Entities existing = new Entities();
        String input = request.getAttribute("id").toString();
        Entity table = existing.getTableByName(request.getAttribute("table").toString());

        Row e = selectByPrimary(table, input);

        response.getWriter().append((e != null) ? gson.toJson(e) : "{}");


    }

    public static void getEntityByColumn(HttpServletRequest request, HttpServletResponse response) throws IOException, SQLException, ResourceNotFoundException {
        Entities existing = new Entities();

        String colName = request.getAttribute("column").toString();
        String target = request.getAttribute("target").toString();
        Entity table = existing.getTableByName(request.getAttribute("table").toString());

        LinkedList<Row> e = EntitiesSQL.selectByColumn(table, colName, target);

        for (Row r : e) {
            response.getWriter().append((r != null) ? gson.toJson(r) : "{}");
        }
    }


    public static void addEntity(HttpServletRequest request, HttpServletResponse response) throws IOException, SQLException {
        Entities existing = new Entities();
        LinkedList<String> data = (LinkedList<String>) request.getAttribute("data");
        Entity table = existing.getTableByName(request.getAttribute("table").toString());
        Row r = EntitiesSQL.insertRow(table, data);
        response.getWriter().append((r != null) ? gson.toJson(r) : "{}");

    }

    public static void updateEntity(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Entities existing = new Entities();
        Entity table = existing.getTableByName(request.getAttribute("table").toString());
        LinkedList<String> data = (LinkedList<String>) request.getAttribute("data");

        if (table.getColumnNames().stream().anyMatch(name -> name.equals(data.get(0)))) {
            String colName = data.get(0);
            data.remove(0);
            LinkedList<Row> output = EntitiesSQL.updateByColumn(table, colName, data);
            for (Row r : output) {
                response.getWriter().append((r != null) ? gson.toJson(r) : "{}");
            }
        } else {

            Row output = EntitiesSQL.updateByPrimary(table, data);
            response.getWriter().append((output != null) ? gson.toJson(output) : "{}");
        }
    }
    public static void deleteEntityById(HttpServletRequest request, HttpServletResponse response) throws ResourceNotFoundException, IOException {

        es.deleteEntityById(request,response);

    }


    public void deleteEntityByColumn(HttpServletRequest request, HttpServletResponse response) throws IOException, SQLException {


       es.deleteEntityByColumn(request, response);
    }


}





