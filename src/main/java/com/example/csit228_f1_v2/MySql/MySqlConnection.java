package com.example.csit228_f1_v2.MySql;
import java.sql.*;

public class MySqlConnection {
    private static final String URL = "jdbc:mysql://localhost:3306/anything";
    private static final String uname = "root";
    private static final String pass = "";
    public static Connection getConnection(){
        Connection c = null;
        try {
            c = DriverManager.getConnection(URL, uname, pass);
            System.out.println("DB Connection successful!!!!!!!!!!");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return c;
    }

    public static ResultSet readData() {
        ResultSet returner = null;

        return returner;
    }

    public static void main(String[] args) {
        getConnection();
    }
}
