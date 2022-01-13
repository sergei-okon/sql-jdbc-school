package ua.com.foxminded.db.dao;

import org.springframework.stereotype.Repository;
import ua.com.foxminded.db.DataSource;
import ua.com.foxminded.model.Course;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Repository
public class CourseDao {
    
    private final DataSource dataSource;

    public CourseDao(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public Course findByName(String name) {
        Course course = new Course();

        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection
                     .prepareStatement("SELECT course_id, name,description FROM courses WHERE name LIKE ?")) {

            preparedStatement.setString(1, name);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                course.setId(resultSet.getInt("course_id"));
                course.setName(resultSet.getString("name"));
                course.setDescription(resultSet.getString("description"));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return course;
    }

    public Course findById(Integer id) {
        Course course = new Course();

        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection
                     .prepareStatement("SELECT course_id, name, description FROM courses WHERE course_id=?")) {

            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                course.setId(resultSet.getInt("course_id"));
                course.setName(resultSet.getString("name"));
                course.setDescription(resultSet.getString("description"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return course;
    }

    public List<Course> findAll() {
        List<Course> courses = new ArrayList<>();

        try (Statement statement = dataSource.getConnection().createStatement()) {
            ResultSet resultSet = statement.executeQuery("SELECT course_id, name,description FROM courses");
            courses = courseMapper(resultSet);

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return courses;
    }

    public void create(Course course) {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection
                     .prepareStatement("INSERT INTO courses(name, description) VALUES (?, ?);")) {

            preparedStatement.setString(1, course.getName());
            preparedStatement.setString(2, course.getDescription());

            preparedStatement.executeUpdate();
            System.out.println("Added new course " + course);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void update(Integer id, Course updateCourse) {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection
                     .prepareStatement("UPDATE courses SET name=?, description=? where course_id=?;")) {

            preparedStatement.setString(1, updateCourse.getName());
            preparedStatement.setString(2, updateCourse.getDescription());
            preparedStatement.setInt(3, id);
            preparedStatement.executeUpdate();

            System.out.println("Update Course: " + updateCourse.getName());

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void delete(Integer id) {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection
                     .prepareStatement("DELETE FROM courses where course_id=?;")) {

            preparedStatement.setInt(1, id);

            preparedStatement.executeUpdate();
            System.out.println("Delete Course: " + id + this.findById(id));
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private List<Course> courseMapper(ResultSet resultSet) throws SQLException {
        List<Course> courses = new ArrayList<>();

        while (resultSet.next()) {
            Course course = new Course();

            course.setId(resultSet.getInt("course_id"));
            course.setName(resultSet.getString("name"));
            course.setDescription(resultSet.getString("description"));

            courses.add(course);
        }
        return courses;
    }
}
