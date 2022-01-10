package ua.com.foxminded.db.dao;

import org.springframework.stereotype.Component;
import ua.com.foxminded.db.DataSource;
import ua.com.foxminded.model.Student;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Component
public class StudentDao {
    private final CourseDao courseDao;

    public StudentDao() {
        courseDao = new CourseDao();
    }

    public List<Student> findAllStudentByCourseName(String name) {
        List<Student> students = new ArrayList<>();

        Integer courseId = courseDao.findByName(name).getId();
        System.out.println("Id course  " + courseId);

        try (Connection connection = DataSource.getConnection();
             PreparedStatement preparedStatement = connection
                     .prepareStatement("  SELECT student_id FROM students_courses WHERE courses_id=?")) {

            preparedStatement.setInt(1, courseId);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                Integer id = resultSet.getInt("student_id");
                students.add(findById(id));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return students;
    }

    public void removeStudentFromCourses(Integer studentId, Integer coursesId) {

        try (Connection connection = DataSource.getConnection();
             PreparedStatement preparedStatement = connection
                     .prepareStatement(" DELETE FROM  students_courses  WHERE (student_id=? AND courses_id=?) ;")) {

            preparedStatement.setInt(1, studentId);
            preparedStatement.setInt(2, coursesId);

            preparedStatement.executeUpdate();
            System.out.println("Remove Student " + this.findById(studentId) + "from the course " + courseDao.findById(coursesId));

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Integer addStudentToCourseById(Integer studentId, Integer courseId) {
        try (Connection connection = DataSource.getConnection();
             PreparedStatement preparedStatement = connection
                     .prepareStatement("INSERT INTO students_courses(student_id, courses_id) VALUES (?, ?)",
                             Statement.RETURN_GENERATED_KEYS)) {

            preparedStatement.setInt(1, studentId);
            preparedStatement.setInt(2, courseId);

            int affectedRows = preparedStatement.executeUpdate();

            if (affectedRows == 0) {
                throw new SQLException("Creating user failed, no rows affected.");
            }

            ResultSet generatedKeys = preparedStatement.getGeneratedKeys();
            if (generatedKeys.next()) {
                return generatedKeys.getInt(1);
            } else {
                throw new RuntimeException("Creating entity failed, no Id obtained.");
            }

        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public Student findById(Integer id) {
        Student student = new Student();

        try (Connection connection = DataSource.getConnection();
             PreparedStatement preparedStatement = connection
                     .prepareStatement("SELECT student_id, first_name, last_name, group_id FROM students WHERE student_id=?")) {
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                student.setId(resultSet.getInt("student_id"));
                student.setFirstName(resultSet.getString("first_name"));
                student.setLastName(resultSet.getString("last_name"));
                student.setGroupId(resultSet.getInt("group_id"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return student;
    }

    public List<Student> findAll() {
        List<Student> students = new ArrayList<>();

        try (Connection connection = DataSource.getConnection();
             PreparedStatement preparedStatement = connection
                     .prepareStatement("SELECT student_id, first_name, last_name, group_id FROM STUDENTS")) {

            ResultSet resultSet = preparedStatement.executeQuery();
            students = StudentMapper(resultSet);

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return students;
    }

    public void create(Student student) {

        try (Connection connection = DataSource.getConnection();
             PreparedStatement preparedStatement = connection
                     .prepareStatement("INSERT INTO students(first_name, last_name, group_id) VALUES( ?, ?, ?)")) {

            preparedStatement.setString(1, student.getFirstName());
            preparedStatement.setString(2, student.getLastName());
            preparedStatement.setInt(3, student.getGroupId());
            preparedStatement.executeUpdate();

            System.out.println("Added new student " + student);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void update(Integer id, Student updateStudent) {

        try (Connection connection = DataSource.getConnection();
             PreparedStatement preparedStatement = connection
                     .prepareStatement("UPDATE students SET  first_name=?, last_name=?, group_id=? where student_id=?")) {

            preparedStatement.setString(1, updateStudent.getFirstName());
            preparedStatement.setString(2, updateStudent.getLastName());
            preparedStatement.setInt(3, updateStudent.getGroupId());
            preparedStatement.setInt(4, id);

            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void delete(Integer id) {

        try (Connection connection = DataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement
                     ("DELETE FROM students_courses WHERE student_id=?; DELETE FROM STUDENTS where student_id=?;")) {

            preparedStatement.setInt(1, id);
            preparedStatement.setInt(2, id);

            preparedStatement.executeUpdate();

            System.out.println("Delete Student: " + id);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private List<Student> StudentMapper(ResultSet resultSet) throws SQLException {
        List<Student> students = new ArrayList<>();

        while (resultSet.next()) {
            Student student = new Student();

            student.setId(resultSet.getInt("student_id"));
            student.setFirstName(resultSet.getString("first_name"));
            student.setLastName(resultSet.getString("last_name"));
            student.setGroupId(resultSet.getInt("group_id"));

            students.add(student);
        }
        return students;
    }
}
