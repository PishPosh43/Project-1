package util;

import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Objects;
import java.util.Properties;

public class JDBCConnection {
    private static Connection conn = null;

    public static Connection getConnection() {
         /*
        Establish new connection or return old one

         */

        if (conn == null) {
            Properties props = new Properties();

            try {
                Class.forName("org.postgresql.Driver");
                 props.load(JDBCConnection.class.getClassLoader().getResourceAsStream("connection.properties"));


                String endpoint = props.getProperty("endpoint");
                String url = "jdbc:postgresql://" + endpoint + "/postgres";
                String username = props.getProperty("username");
                String password = props.getProperty("password");


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