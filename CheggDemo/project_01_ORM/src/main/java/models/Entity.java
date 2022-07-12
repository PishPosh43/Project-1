package models;

import repository.EntitiesSQL;
import repository.Row;

import java.util.LinkedList;

public class Entity {
    private String name; //Table name
    private LinkedList<String> columnNames;//All column names
    private LinkedList<String> columnData;//Data type stored in each column
    private int primaryColumnIndex; //The index of the column where the primary key is, -1 if none exists which could be used to deny any requests to perform any functions by id
    private LinkedList<Row> rows; //All data stored in the table is stored in individual rows

    public Entity() {

    }

    public Entity(String name) {

        this.name = name;
        this.columnNames = EntitiesSQL.getColumnNames(name);
        this.columnData = EntitiesSQL.getColumnData(name);
        this.primaryColumnIndex = EntitiesSQL.getPrimaryColumn(this);
        rows = EntitiesSQL.setRows(this);//set rows takes an Entity so the existing entity is passed
    }



    public LinkedList<String> getColumnData() {
        return columnData;
    }

    public void setColumnData(LinkedList<String> columnData) {
        this.columnData = columnData;
    }

    public int getPrimaryColumnIndex() {
        return primaryColumnIndex;
    }

    public void setPrimaryColumnIndex(int primaryColumnIndex) {
        this.primaryColumnIndex = primaryColumnIndex;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LinkedList<String> getColumnNames() {
        return columnNames;
    }

    public void setColumnNames(LinkedList<String> columnNames) {
        this.columnNames = columnNames;
    }

    public LinkedList<Row> getRows() {
        return rows;
    }

    public void setRows(LinkedList<Row> rows) {
        this.rows = rows;
    }

    @Override
    public String toString() {


        {
            String output = "Table name:  " + this.name + "\n";
            for (int i = 0; i < columnNames.size(); i++) {
                output += columnNames.get(i) + "\t";
            }
            output += "\n";
            for (int i = 0; i < rows.size(); i++) {
                output += rows.get(i) + "\n";
            }
            return output;
        }
    }

}

