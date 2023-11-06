package org.apache.maven.archetypes.homework;

import org.apache.maven.archetypes.homework.Homework;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class HomeworkDao {
    private Connection connection;

    public HomeworkDao(Connection connection) {
        this.connection = connection;
    }

    public Homework getHomeworkById(int homeworkId) {
        String query = "SELECT * FROM homeworks WHERE id = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, homeworkId);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    String name = resultSet.getString("name");
                    String description = resultSet.getString("description");
                    return new Homework(homeworkId, name, description);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
