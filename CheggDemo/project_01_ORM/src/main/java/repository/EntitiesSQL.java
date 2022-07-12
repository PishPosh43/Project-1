package repository;

import models.Entities;
import models.Entity;
import util.JDBCConnection;
import util.ResourceNotFoundException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.stream.Collectors;

/*
Add selectByColumnName, deleteByColumnName, updateByColumnName, and displayColumnNames
 */

public class EntitiesSQL {
    private static final String SCHEMA_NAME = "public"; //Name of the schema being used
    static Connection conn = JDBCConnection.getConnection();

    /**
     * Used to initialize the Entities class object
     *
     * @return A linked list of all existing tables is returned
     */
    public static LinkedList<Entity> populate() {
        String sql = "select table_name from information_schema.tables where table_schema = '" + SCHEMA_NAME + "';";
        Connection conn = JDBCConnection.getConnection();
        LinkedList<String> tableNames = new LinkedList<>();

        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                tableNames.add(rs.getString(1));
            }
            LinkedList<Entity> entityList = new LinkedList<>();
            for (String name : tableNames) {
                entityList.add(new Entity(name));
            }
            return entityList;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }






    /**
     * Used to initialize the column names list for an entity in the constructor
     *
     * @param tableName The name of the table being initialized is passed in
     * @return Returns a linked list of all column names
     */
    public static LinkedList<String> getColumnNames(String tableName) {
        String sql = "select column_name from information_schema.columns where table_schema = '" + SCHEMA_NAME + "' and table_name = '" + tableName + "';";
        Connection conn = JDBCConnection.getConnection();
        LinkedList<String> tableNames = new LinkedList<>();

        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                tableNames.add(rs.getString(1));
            }
            return tableNames;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Used to initialize the column data list for an entity in the constructor
     *
     * @param tableName The name of the table being initialized is passed in
     * @return Returns a linked list of all column data types
     */
    public static LinkedList<String> getColumnData(String tableName) {
        String sql = "select data_type from information_schema.columns where table_schema = '" + SCHEMA_NAME + "' and table_name = '" + tableName + "';";
        Connection conn = JDBCConnection.getConnection();
        LinkedList<String> columnNames = new LinkedList<>();

        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                columnNames.add(rs.getString(1));
            }
            return columnNames;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Used to initialize the existing rows in the table
     *
     * @param table The table being passed in
     * @return Returns a linked list of all rows in the table
     */
    public static LinkedList<Row> setRows(Entity table) {
        String sql = "select * from " + table.getName();
        Connection conn = JDBCConnection.getConnection();
        LinkedList rows = new LinkedList<Row>();
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            return buildRows(table, rows, rs);
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }

    }

    private static Row buildRow(Entity table, ResultSet rs) {
        Row newRow = new Row(table);
        for (int i = 1; i < newRow.getNumCols() + 1; i++) {
            try {
                newRow.getValues().add(rs.getString(i));
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return newRow;
    }

    private static LinkedList buildRows(Entity table, LinkedList rows, ResultSet rs) throws SQLException {
        while (rs.next()) {
            Row newRow = new Row(table);
            for (int i = 1; i < newRow.getNumCols() + 1; i++) {
                newRow.getValues().add(rs.getString(i));
            }
            rows.add(newRow);
        }
        return rows;
    }

    /**
     * Inserts a new row into the requested table with the data defined
     *
     * @param table The target table
     * @param data  All data to be inserted, in order
     * @return returns the new row that was created
     */
    public static Row insertRow(Entity table, LinkedList<String> data) {
        String sql = "INSERT INTO " + table.getName() + " VALUES (";
        for (int i = 0; i < table.getColumnNames().size(); i++) {
            if (i == table.getPrimaryColumnIndex())//using default for primary keys that are numbers
            {
                sql += " default";
            } else {
                sql += " ?";
            }
            if (i != table.getColumnNames().size() - 1)//Inserting a comma if it is not the last column
            {
                sql += ",";
            }
        }
        sql += ") returning *";
        Connection conn = JDBCConnection.getConnection();
        try {
            PreparedStatement ps = conn.prepareStatement(sql);

            //setting data to be inserted
            int checkNum = 0;
            int x = 0; //needed to increment data inside the for loop as it is not 1 indexed like PreparedStatement
            for (int i = 1; i <= table.getColumnNames().size() && x < data.size(); i++) //<= used here because PreparedStatement is 1 indexed
            {
                if (checkNum == table.getPrimaryColumnIndex()) {
                    checkNum++;
                }

                if (table.getColumnData().get(checkNum).equals("character varying") || table.getColumnData().get(checkNum).equals("character")) {
                    ps.setString(i, data.get(x));
                } else if (table.getColumnData().get(checkNum).equals("integer") || table.getColumnData().get(checkNum).equals("number")) {
                    ps.setInt(i, Integer.parseInt(data.get(x)));
                } else if (table.getColumnData().get(checkNum).equals("boolean")) {
                    ps.setBoolean(i, Boolean.parseBoolean(data.get(x)));
                }
                x++;//incrementing x
                checkNum++;
            }
            ResultSet rs = ps.executeQuery();

            Row newRow = new Row(table);
            if (rs.next()) {
                for (int i = 1; i < newRow.getNumCols() + 1; i++) {

                    newRow.getValues().add(rs.getString(i));
                }
            }
            table.getRows().add(newRow);
            return newRow;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * This method finds the column that the primary key is located in and returns the index as an int
     *
     * @param table the table being searched
     * @return returns the index of the found primary key, or -1 if none is found
     */
    public static int getPrimaryColumn(Entity table) {
        //This extremely long sql statement returns the column name where the primary key is
        String sql = "SELECT Col.Column_Name from INFORMATION_SCHEMA.TABLE_CONSTRAINTS Tab, INFORMATION_SCHEMA.CONSTRAINT_COLUMN_USAGE Col WHERE Col.Constraint_Name = Tab.Constraint_Name AND Col.Table_Name = Tab.Table_Name AND Constraint_Type = 'PRIMARY KEY' AND Col.Table_Name = '" + table.getName() + "'";
        Connection conn = JDBCConnection.getConnection();

        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                String primaryCol = rs.getString("column_name");
                for (int i = 0; i < table.getColumnNames().size(); i++) {
                    if (primaryCol.equals(table.getColumnNames().get(i))) ;
                    {
                        return i;
                    }
                }

            }
            return -1;
        } catch (SQLException e) {
            e.printStackTrace();
            return -1;
        }
    }

    public static void deleteByPrimary(Entity table, String target) {
        String sql = "DELETE from " + table.getName() + " where " + table.getColumnNames().get(table.getPrimaryColumnIndex()) + " = " + target;
        Connection conn = JDBCConnection.getConnection();

        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.executeUpdate();

            for (int i = 0; i < table.getRows().size(); i++) {
                if (table.getRows().get(i).getValues().get(table.getPrimaryColumnIndex()).equals(target)) {
                    //updating the row to the new data
                    table.getRows().remove(i);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static Row selectByPrimary(Entity table, String target) {
        for(Row r:table.getRows()) {
            if (r.getValues().get(table.getPrimaryColumnIndex()).equals(target)) {
                return r;
            }
        }
        return null;


    }

    /**
     * @param table   table being updated
     * @param newData Data to be updated, first value MUST be the primary key being updated, next values are values being changed
     * @return returns newly updated row, returns null if an error occurs
     */
    public static Row updateByPrimary(Entity table, LinkedList<String> newData) {
        //Setting up the sql statement
        String sql = "UPDATE " + table.getName() + " SET ";
        int newDataCounter = 1;
        //while there are still columns to be set and data to be set, keep looping
        for (int i = 0; i < table.getColumnNames().size() && newDataCounter < newData.size(); i++) {
            //skip inserting at primary key
            if (i != table.getPrimaryColumnIndex()) {
                if (!table.getColumnData().get(i).equals("character varying")) {
                    sql += table.getColumnNames().get(i) + " = " + newData.get(newDataCounter);
                } else sql += table.getColumnNames().get(i) + " = '" + newData.get(newDataCounter) + "'";
                newDataCounter++;
                //add the , when not at the end
                if (newDataCounter < newData.size() && i + 1 < table.getColumnNames().size()) {
                    sql += ", ";
                }
            }
        }
        //setting the sql statement to update at the requested id
        sql += " WHERE " + table.getColumnNames().get(table.getPrimaryColumnIndex()) + " = " + newData.get(0);
        Connection conn = JDBCConnection.getConnection();
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.executeUpdate();
            //no result set used here because it does not return any results for this query

            //updating the ORM with the new data
            sql = "SELECT * FROM " + table.getName() + " WHERE " + table.getColumnNames().get(table.getPrimaryColumnIndex()) + " = " + newData.get(0);
            ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                Row newRow = buildRow(table, rs);
                //finding the row to be updated
                for (int i = 0; i < table.getRows().size(); i++) {
                    if (table.getRows().get(i).getValues().get(table.getPrimaryColumnIndex()).equals(newData.get(0))) {
                        //updating the row to the new data
                        table.getRows().set(i, newRow);
                    }
                }
                return newRow;
            }
            return null;//if the data can't be found for whatever reason null is returned
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static LinkedList<Row> selectByColumn(Entity table, String colName, String target)
    {
        int colNum = getColNum(table, colName);
        if(colNum==-1)
        {
            return null;
        }
        else {
            return table.getRows().stream().filter(row -> row.getValues().get(colNum).equals(target)).collect(Collectors.toCollection(LinkedList::new));
        }
    }


    private static int getColNum(Entity table, String colName) {
        for (int i = 0; i < table.getColumnNames().size(); i++) {
            if (table.getColumnNames().get(i).equals(colName)) {
                return i;
            }
        }
        return -1;
    }

    public static void deleteByColumn(Entity table, String colName, String target) {
        int colNum = getColNum(table, colName);
        String sql = "DELETE from " + table.getName() + " where " + colName + " = ";
        if (table.getColumnData().get(colNum).equals("character varying")) {
            sql += "'" + target + "'";
        } else sql += target;
        Connection conn = JDBCConnection.getConnection();

        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.executeUpdate();
            for (int i = 0; i < table.getRows().size(); i++) {
                if (table.getRows().get(i).getValues().get(colNum).equals(target)) {
                    table.getRows().remove(i);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * @param table   table being updated
     * @param colName column being updating on
     * @param newData Data to be updated, first value MUST be the column value being updated, next values are values being changed
     * @return returns LinkedList of updated rows, returns null if an error occurs
     */
    public static LinkedList<Row> updateByColumn(Entity table, String colName, LinkedList<String> newData) {
        int colNum = getColNum(table, colName);
        //Setting up the sql statement
        String sql = "UPDATE " + table.getName() + " SET ";
        int newDataCounter = 1;
        //while there are still columns to be set and data to be set, keep looping
        for (int i = 0; i < table.getColumnNames().size() && newDataCounter < newData.size(); i++) {
            //skip inserting at primary key
            if (i != table.getPrimaryColumnIndex()) {
                sql += table.getColumnNames().get(i) + " = ";
                if (table.getColumnData().get(i).equals("character varying")) {
                    sql += "'" + newData.get(i) + "'";
                } else sql += newData.get(i);
                newDataCounter++;
                //add the , when not at the end
                if (newDataCounter < newData.size() && i + 1 < table.getColumnNames().size()) {
                    sql += ", ";
                }
            }
        }
        //setting the sql statement to update at the requested id
        sql += " WHERE " + colName + " = ";
        if (table.getColumnData().get(colNum).equals("character varying")) {
            sql += "'" + newData.get(0) + "'";
        } else sql += newData.get(0);
        Connection conn = JDBCConnection.getConnection();


        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.executeUpdate();
            //no result set used here because it does not return any results for this query

            //updating the ORM with the new data
            sql = "SELECT * FROM " + table.getName() + " WHERE " + table.getColumnNames().get(colNum) + " = ";
            if (table.getColumnData().get(colNum).equals("character varying")) {
                sql += "'" + newData.get(colNum) + "'";
            } else sql += newData.get(colNum);
            ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            LinkedList<Row> updatedRows = new LinkedList<>();
            while (rs.next()) {
                //finding the rows to be updated
                for (int i = 0; i < table.getRows().size(); i++) {
                    if (table.getRows().get(i).getValues().get(colNum).equals(newData.get(0))) {
                        Row newRow = buildRow(table, rs);
                        //updating the row to the new data
                        table.getRows().set(i, newRow);
                        updatedRows.add(newRow);
                    }
                }
                return updatedRows;
            }
            return null;//if the data can't be found for whatever reason null is returned
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static String selectAll(Entity table) {
        return table.toString();
    }


}