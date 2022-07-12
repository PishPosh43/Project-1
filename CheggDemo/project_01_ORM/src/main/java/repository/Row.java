package repository;

import models.Entity;

import java.util.LinkedList;

public class Row {
    private int numCols; //number of columns in the row
    private LinkedList<String> colNames;//names of each column
    private LinkedList<String> values;//the value of each column

    public Row(Entity table)
    {
        colNames = table.getColumnNames();// Get the names of all the columns 
        numCols = colNames.size();//number of columns is equal to the number of column names
        values = new LinkedList<String>();//Create a LinkedList to hold values
    }

    public int getNumCols() {
        return numCols;
    }

    public void setNumCols(int numCols) {
        this.numCols = numCols;
    }

    public LinkedList<String> getColNames() {
        return colNames;
    }

    public void setColNames(LinkedList<String> colNames) {
        this.colNames = colNames;
    }

    public LinkedList<String> getValues() {
        return values;
    }

    public void setValues(LinkedList<String> values) {
        this.values = values;
    }

    public String toString()
    {
        String output ="";
        for (int i =0;i<numCols;i++)
        {
            output+= values.get(i)+"\t";
        }
        return output;
    }
}


