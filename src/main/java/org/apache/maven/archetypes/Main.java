package org.apache.maven.archetypes;

import java.sql.Connection;

public class Main {
    public static void main(String[] args) {
        DataBaseConnection dbConnection = new DataBaseConnection();

        Connection connection = dbConnection.getConnection();

        dbConnection.close();
    }
}