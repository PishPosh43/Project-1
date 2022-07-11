package com.revature.util;

import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Objects;
import java.util.Properties;

public class ConnectionUtil {
    private static Connection conn = null;

    public static Connection getConnection() {
         /*
        Establishes new connection if one does not exist
         */

        if (conn == null) {
            Properties props = new Properties();
            
            
            // This will load the driver, and while loading, the driver will automatically register itself with JDBC
            try {
                Class.forName("org.postgresql.Driver");
                 props.load(ConnectionUtil.class.getClassLoader().getResourceAsStream("connection.properties"));


                
                String url = "jdbc:postgresql://testdb.ctq76wvsaip.us-east-2.rds.amazonaws.com//postgres";
                String username = "postgres";
                String password = "testpassword";


                conn = DriverManager.getConnection(url, username, password);//getConnection(url, username, password);

            } catch (SQLException | IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        }

        return conn;
    }

    public static void main(String[] args) {
        Connection conn1 = getConnection();
        Connection conn2 = getConnection();


        System.out.println(conn1);
        System.out.println(conn2);
    }
}