package ua.com.foxminded.db.dao;

import ua.com.foxminded.db.DataSource;

import java.sql.*;

public class TestUtils {
    public Integer createStudent(String expectedFirstName, String expectedLastName) throws SQLException {
        try (Connection connection = DataSource.getConnection()) {
            PreparedStatement preparedStatement =
                    connection.prepareStatement("INSERT INTO students(first_name, last_name) VALUES(?, ?)", Statement.RETURN_GENERATED_KEYS);

            preparedStatement.setString(1, expectedFirstName);
            preparedStatement.setString(2, expectedLastName);

            Integer affectedRows = preparedStatement.executeUpdate();

            if (affectedRows == 0) {
                throw new SQLException("Creating user failed, no rows affected.");
            }

            ResultSet generatedKeys = preparedStatement.getGeneratedKeys();
            if (generatedKeys.next()) {
                return generatedKeys.getInt(1);
            } else {
                throw new RuntimeException("Creating entity failed, no Id obtained.");
            }
        }
    }

    public Integer createGroup(String expectedGroupName, Integer expectedGroupAmountStudents) throws SQLException {
        try (Connection connection = DataSource.getConnection()) {

            PreparedStatement preparedStatement =
                    connection.prepareStatement("INSERT INTO groups(name, amount_students) VALUES(?, ?)", Statement.RETURN_GENERATED_KEYS);

            preparedStatement.setString(1, expectedGroupName);
            preparedStatement.setInt(2, expectedGroupAmountStudents);

            Integer generatedGroupId = preparedStatement.executeUpdate();

            if (generatedGroupId == 0) {
                throw new SQLException("Creating group failed, no rows affected.");
            }

            ResultSet generatedKeys = preparedStatement.getGeneratedKeys();
            if (generatedKeys.next()) {
                return generatedKeys.getInt(1);
            } else {
                throw new RuntimeException("Creating entity failed, no Id obtained.");
            }
        }
    }

    public Integer createCourse(String expectedCourseName, String expectedCourseDescription) throws SQLException {
        try (Connection connection = DataSource.getConnection()) {

            PreparedStatement preparedStatement =
                    connection.prepareStatement("INSERT INTO courses(name, description) VALUES(?, ?)", Statement.RETURN_GENERATED_KEYS);

            preparedStatement.setString(1, expectedCourseName);
            preparedStatement.setString(2, expectedCourseDescription);

            Integer generatedCourseId = preparedStatement.executeUpdate();

            if (generatedCourseId == 0) {
                throw new SQLException("Creating course failed, no rows affected.");
            }

            ResultSet generatedKeys = preparedStatement.getGeneratedKeys();
            if (generatedKeys.next()) {
                return generatedKeys.getInt(1);
            } else {
                throw new RuntimeException("Creating entity failed, no Id obtained.");
            }
        }
    }
}
