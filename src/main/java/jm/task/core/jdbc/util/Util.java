package jm.task.core.jdbc.util;


import java.sql.*;

public class Util {
    private static final String username = "root";
    private static final String password = "Lostsoul7007";
    private static final String url = "jdbc:mysql://localhost:3306/new_schema?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC";
    private Connection connection;

    public Connection getConnection() {
        try {
            connection = DriverManager.getConnection(url, username, password);
        } catch (SQLException e) {
            System.out.println("Connection failure");
            e.printStackTrace(System.out);
        }
        return connection;
    }

    public void closeConnection() {
        try {
            connection.close();
        } catch (SQLException e) {
            System.out.println("Connection closure failure");
            e.printStackTrace(System.out);
        }
    }
}
