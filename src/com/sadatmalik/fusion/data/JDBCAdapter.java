package com.sadatmalik.fusion.data;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class JDBCAdapter {

    private final static String JDBC_DRIVER_CLASS_NAME = "com.mysql.cj.jdbc.Driver";
    private static Connection connection;

    private final static String HOSTNAME = "localhost";
    private final static String PORT = "3306";
    private final static String DATABASE = "fusion";
    private final static String USER = "sadat";
    private final static String PASSWORD = "pa55word";

    public JDBCAdapter() {
        // 1. Load and initialise the JDBC driver
        try {
            Class.forName(JDBC_DRIVER_CLASS_NAME);

            // 2. Open a connection
            connection = DriverManager.getConnection("jdbc:mysql://" + HOSTNAME + ":" + PORT +
                    "/" + DATABASE, USER, PASSWORD);

            System.out.println("Connected to Database: " + HOSTNAME + ":" + PORT + "/" + DATABASE);

        } catch (ClassNotFoundException exception) {
            System.out.println("Unable to load JDBC driver");
            exception.printStackTrace();
        } catch (SQLException exception) {
            System.out.println("Unable to open a connection to " + "jdbc:mysql://" + HOSTNAME + ":" + PORT +
            "/" + DATABASE);
            exception.printStackTrace();
        }

    }

    public static void main(String[] args) {
        JDBCAdapter adapter = new JDBCAdapter();
    }

}
