package ua.com.foxminded.dao;

import ua.com.foxminded.model.Course;
import ua.com.foxminded.model.Student;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class StudentDao {

    public List<Student> findAllStudentsRelatedToCourseByCourseName(String nameCourse) {
        List<Student> students = new ArrayList<>();
        StudentDao studentDao = new StudentDao();
        CourseDao courseDao = new CourseDao();

        int courseId = courseDao.findByName(nameCourse).getId();
        System.out.println("Id курса  " + courseId);

        try (Connection connection = DataSource.getConnection();
             PreparedStatement preparedStatement = connection
                     .prepareStatement("  SELECT student_id FROM students_courses WHERE courses_id=?")) {

            preparedStatement.setInt(1, courseId);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                int id = resultSet.getInt("student_id");
                students.add(studentDao.findById(id));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return students;
    }

    public void removeStudentFromCourses(int studentId, int coursesId) {
        CourseDao courseDao = new CourseDao();

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

    public void addStudentToCourseById(int studentId, int courseId) {
        try (Connection connection = DataSource.getConnection();
             PreparedStatement preparedStatement = connection
                     .prepareStatement("INSERT INTO students_courses(student_id, courses_id)VALUES(?,?)")) {

            preparedStatement.setInt(1, studentId);
            preparedStatement.setInt(2, courseId);

            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void addStudentToCourseByName(Student student, Course courses) {

        try (Connection connection = DataSource.getConnection();
             PreparedStatement preparedStatement = connection
                     .prepareStatement("INSERT INTO students_courses(student_id, courses_id)VALUES(?,?)")) {

            preparedStatement.setInt(1, student.getId());
            preparedStatement.setInt(2, courses.getId());

            preparedStatement.executeUpdate();
            System.out.println("Added Student to the course: " + student.getFirstName() + " " + student.getLastName());

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Student findById(int id) {
        Student student = new Student();

        try (Connection connection = DataSource.getConnection();
             PreparedStatement preparedStatement = connection
                     .prepareStatement("SELECT * FROM students WHERE student_id=?")) {

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

    public void addNew(Student student) {

        try (Connection connection = DataSource.getConnection();
             PreparedStatement preparedStatement = connection
                     .prepareStatement("INSERT INTO students(first_name, last_name, group_id) VALUES( ?, ?, ?)")) {

            preparedStatement.setString(1, student.getFirstName());
            preparedStatement.setString(2, student.getLastName());
            preparedStatement.setInt(3, student.getGroupId());
            preparedStatement.executeUpdate();

            System.out.println("Added new student " + student);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void update(int id, Student updateStudent) {

        try (Connection connection = DataSource.getConnection();
             PreparedStatement preparedStatement = connection
                     .prepareStatement("UPDATE students SET  first_name=?, last_name=?, group_id=? where student_id=?;")) {

            preparedStatement.setString(1, updateStudent.getFirstName());
            preparedStatement.setString(2, updateStudent.getLastName());
            preparedStatement.setInt(3, updateStudent.getGroupId());
            preparedStatement.setInt(4, id);

            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void delete(int id) {

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
