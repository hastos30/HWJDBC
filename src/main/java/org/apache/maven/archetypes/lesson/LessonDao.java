package org.apache.maven.archetypes.lesson;

import org.apache.maven.archetypes.homework.Homework;
import org.apache.maven.archetypes.homework.HomeworkDao;
import org.apache.maven.archetypes.lesson.Lesson;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class LessonDao {
    private Connection connection;

    public LessonDao(Connection connection) {
        this.connection = connection;
    }

    public void addLesson(Lesson lesson) {
        String query = "INSERT INTO lessons (name, homework_id) VALUES (?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, lesson.getName());
            statement.setInt(2, lesson.getHomework().getId());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteLesson(int lessonId) {
        String query = "DELETE FROM lessons WHERE id = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, lessonId);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Lesson> getAllLessons() {
        List<Lesson> lessons = new ArrayList<>();
        String query = "SELECT * FROM lessons";
        try (PreparedStatement statement = connection.prepareStatement(query);
             ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String name = resultSet.getString("name");
                int homeworkId = resultSet.getInt("homework_id");
                HomeworkDao homeworkDao = new HomeworkDao(connection);
                Homework homework = homeworkDao.getHomeworkById(homeworkId);
                Lesson lesson = new Lesson(id, name, homework);
                lessons.add(lesson);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return lessons;
    }

    public Lesson getLessonById(int lessonId) {
        String query = "SELECT * FROM lessons WHERE id = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, lessonId);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    String name = resultSet.getString("name");
                    int homeworkId = resultSet.getInt("homework_id");
                    HomeworkDao homeworkDao = new HomeworkDao(connection);
                    Homework homework = homeworkDao.getHomeworkById(homeworkId);
                    return new Lesson(lessonId, name, homework);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
