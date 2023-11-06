package org.apache.maven.archetypes;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class DataBaseConnection {
    private Connection connection;

    public DataBaseConnection() {
        Properties properties = new Properties();
        try {
            properties.load(new FileInputStream("D:\\HWJDBC\\src\\main\\resources\\Properties.properties"));

            String url = properties.getProperty("db.url");
            String username = properties.getProperty("db.username");
            String password = properties.getProperty("db.password");

            connection = DriverManager.getConnection(url, username, password);
        } catch (IOException | SQLException e) {
            e.printStackTrace();
        }
    }

    public Connection getConnection() {
        if (connection != null) {
            System.out.println("Підключення до бази даних успішно встановлено");
        }
        return connection;
    }

    public void close() {
        if (connection != null) {
            try {
                System.out.println("Підключення до бази даних успішно завершено");
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}