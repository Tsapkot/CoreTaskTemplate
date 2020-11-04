package jm.task.core.jdbc.util;


import java.io.FileInputStream;
import java.io.IOException;
import java.sql.*;
import java.util.Properties;

public class Util {
    private Connection connection;

    public Connection getConnection() {
        Properties properties = new Properties();

        try (FileInputStream fis = new FileInputStream("src\\main\\java\\jm\\task\\core\\jdbc\\util\\config.properties")) {
            properties.load(fis);
        } catch (IOException e) {
            System.out.println("properties не читаются");
            e.printStackTrace(System.out);
        }

        try {
            connection = DriverManager.getConnection(properties.getProperty("url"), properties.getProperty("username"), properties.getProperty("password"));
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
