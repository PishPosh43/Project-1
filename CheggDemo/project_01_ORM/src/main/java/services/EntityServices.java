package services;


import com.google.gson.Gson;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import models.Entities;
import models.Entity;
import repository.EntitiesSQL;
import repository.Row;
import util.ResourceNotFoundException;

import java.io.IOException;
import java.sql.SQLException;
import java.util.LinkedList;

public class EntityServices {

    static Gson gson = new Gson();

    controllers.EntityController ec;

    public EntityServices(){

    }

    public static void populate(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Entities existing = new Entities();
        Entity table = existing.getTableByName(request.getAttribute("table").toString());
        response.getWriter().append((table != null) ? gson.toJson(table) : "{}");

    }
    public void getEntityById(HttpServletRequest request, HttpServletResponse response) throws IOException, SQLException {
        Entities existing = new Entities();
        String input = request.getAttribute("id").toString();
        Entity table = existing.getTableByName(request.getAttribute("table").toString());
        Row e = EntitiesSQL.selectByPrimary(table, input);
        response.getWriter().append((e != null) ? gson.toJson(e) : "{}");


    }

    public void getEntityByColumn(HttpServletRequest request, HttpServletResponse response) throws IOException, SQLException, ResourceNotFoundException {
        Entities existing = new Entities();

        String colName = request.getAttribute("column").toString();
        String target = request.getAttribute("target").toString();
        Entity table = existing.getTableByName(request.getAttribute("table").toString());

        LinkedList<Row> e = EntitiesSQL.selectByColumn(table, colName, target);

        for (Row r : e) {
            response.getWriter().append((r != null) ? gson.toJson(r) : "{}");
        }
    }


    public void addEntity(HttpServletRequest request, HttpServletResponse response) throws IOException, SQLException {
        Entities existing = new Entities();
        LinkedList<String> data = (LinkedList<String>) request.getAttribute("data");
        Entity table = existing.getTableByName(request.getAttribute("table").toString());
        Row r = EntitiesSQL.insertRow(table, data);
        response.getWriter().append((r != null) ? gson.toJson(r) : "{}");

    }

    public void updateEntity(HttpServletRequest request, HttpServletResponse response) throws IOException {
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
    public void deleteEntityById(HttpServletRequest request, HttpServletResponse response) throws ResourceNotFoundException, IOException {
        Entities existing = new Entities();
        String input = request.getAttribute("id").toString();
        Entity table = existing.getTableByName(request.getAttribute("table").toString());
        EntitiesSQL.deleteByPrimary(table, input);

    }


    public void deleteEntityByColumn(HttpServletRequest request, HttpServletResponse response) throws IOException, SQLException {
        Entities existing = new Entities();
        String colName = request.getAttribute("column").toString();
        String target = request.getAttribute("target").toString();
        Entity table = existing.getTableByName(request.getAttribute("table").toString());

        EntitiesSQL.deleteByColumn(table, colName, target);
    }


}





