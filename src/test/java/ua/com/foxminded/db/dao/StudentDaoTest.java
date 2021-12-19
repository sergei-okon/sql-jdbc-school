package ua.com.foxminded.db.dao;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import ua.com.foxminded.db.DataSource;
import ua.com.foxminded.model.Student;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.sql.*;
import java.util.List;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.*;

class StudentDaoTest extends TestUtils {
    static StudentDao studentDao;

    @BeforeAll
    static void setUp() throws SQLException, IOException {
        Connection connection = DataSource.getConnection();
        Statement statement = connection.createStatement();
        statement.executeUpdate(new String(Objects.requireNonNull(Thread.currentThread()
                .getContextClassLoader().getResourceAsStream("scriptDB/initial_H2.sql")).readAllBytes(),
                StandardCharsets.UTF_8));
        connection.close();
        studentDao = new StudentDao();
    }

    @Test
    void addNew() throws SQLException {
        String expectedFirstName = "Callum";
        String expectedLastName = "Mcfarland";

        int savedStudentId = createStudent(expectedFirstName, expectedLastName);

        try (Connection connection = DataSource.getConnection()) {
            PreparedStatement preparedStatement =
                    connection.prepareStatement("SELECT * FROM students WHERE student_id = ?");

            preparedStatement.setInt(1, savedStudentId);
            ResultSet resultSet = preparedStatement.executeQuery();

            Student actualStudent = new Student();

            while (resultSet.next()) {
                actualStudent.setId(resultSet.getInt("student_id"));
                actualStudent.setFirstName(resultSet.getString("first_name"));
                actualStudent.setLastName(resultSet.getString("last_name"));
                actualStudent.setGroupId(resultSet.getInt("group_id"));

                assertNotNull(actualStudent);
                assertEquals(expectedFirstName, actualStudent.getFirstName());
                assertEquals(expectedLastName, actualStudent.getLastName());
                assertEquals(savedStudentId, actualStudent.getId());
            }
        }
    }

    @Test
    void findById() throws SQLException {
        String expectedFirstName = "Callum";
        String expectedLastName = "Mcfarland";

        int savedStudentId = createStudent(expectedFirstName, expectedLastName);

        Student actual = studentDao.findById(savedStudentId);

        assertEquals(savedStudentId, actual.getId());
        assertEquals(expectedFirstName, actual.getFirstName());
        assertEquals(expectedLastName, actual.getLastName());
    }

    @Test
    void delete() throws SQLException {
        String expectedFirstName = "Callum";
        String expectedLastName = "Mcfarland";

        int savedStudentId = createStudent(expectedFirstName, expectedLastName);

        studentDao.delete(savedStudentId);

        try (Connection connection = DataSource.getConnection()) {
            PreparedStatement preparedStatement =
                    connection.prepareStatement("SELECT * FROM students WHERE student_id = ?");

            preparedStatement.setInt(1, savedStudentId);
            ResultSet resultSet = preparedStatement.executeQuery();
            assertFalse(resultSet.isBeforeFirst());
        } catch (Exception ex) {
            throw new RuntimeException(ex.getMessage());
        }
    }

    @Test
    void findAllStudentsRelatedToCourseByCourseName() {
        String expectedFirstName = "Callum";
        String expectedLastName = "Mcfarland";

        String expectedNameCourse = "Math";
        String expectedDescription = "Mathematics";

        int savedStudentId = 0;
        int savedCourseId = 0;

        try {
            savedStudentId = createStudent(expectedFirstName, expectedLastName);
            savedCourseId = createCourse(expectedNameCourse, expectedDescription);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        try (Connection connection = DataSource.getConnection();

             PreparedStatement preparedStatement = connection
                     .prepareStatement("INSERT INTO students_courses(student_id, courses_id) VALUES (?, ?)")) {

            preparedStatement.setInt(1, savedStudentId);
            preparedStatement.setInt(2, savedCourseId);

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        List<Student> actual = studentDao.findAllStudentsRelatedToCourseByCourseName(expectedNameCourse);

        assertNotNull(actual);
        assertEquals(actual.size(), 1);

        Student actualStudent = actual.get(0);
        assertNotNull(actualStudent);

        assertEquals(expectedFirstName, actualStudent.getFirstName());
        assertEquals(expectedLastName, actualStudent.getLastName());
        assertEquals(savedStudentId, actualStudent.getId());
    }

    @Test
    void addStudentToCourseById() throws SQLException {
        String expectedFirstName = "Callum";
        String expectedLastName = "Mcfarland";

        String expectedNameCourse = "Math";
        String expectedDescription = "Mathematics";

        int savedStudentId = 0;
        int savedCourseId = 0;

        try {
            savedStudentId = createStudent(expectedFirstName, expectedLastName);
            savedCourseId = createCourse(expectedNameCourse, expectedDescription);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        final Integer recordId = studentDao.addStudentToCourseById(savedStudentId, savedCourseId);

        try (Connection connection = DataSource.getConnection()) {
            PreparedStatement preparedStatement =
                    connection.prepareStatement("SELECT student_id, courses_id FROM students_courses WHERE id = ?");

            preparedStatement.setInt(1, recordId);

            final ResultSet resultSet = preparedStatement.executeQuery();
            resultSet.next();
            assertNotNull(resultSet);
            assertEquals(savedStudentId, resultSet.getInt(1));
            assertEquals(savedCourseId, resultSet.getInt(2));
        }
    }

    @Test
    void removeStudentFromCourses() throws SQLException {
        String expectedFirstName = "Callum";
        String expectedLastName = "Mcfarland";

        String expectedNameCourse = "Math";
        String expectedDescription = "Mathematics";

        int savedStudentId = 0;
        int savedCourseId = 0;
        int studentCourseLinkId = 0;

        try {
            savedStudentId = createStudent(expectedFirstName, expectedLastName);
            savedCourseId = createCourse(expectedNameCourse, expectedDescription);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        PreparedStatement preparedStatement = DataSource.getConnection()
                .prepareStatement("INSERT INTO students_courses(student_id, courses_id) VALUES (?, ?)", Statement.RETURN_GENERATED_KEYS);

        preparedStatement.setInt(1, savedStudentId);
        preparedStatement.setInt(2, savedCourseId);

        int affectedRows = preparedStatement.executeUpdate();

        if (affectedRows == 0) {
            throw new SQLException("Creating user failed, no rows affected.");
        }

        ResultSet generatedKeys = preparedStatement.getGeneratedKeys();
        if (generatedKeys.next()) {
            System.out.println("Added Student to the course");
            studentCourseLinkId = generatedKeys.getInt(1);
        } else {
            throw new RuntimeException("Creating entity failed, no Id obtained.");
        }

        studentDao.removeStudentFromCourses(savedStudentId, savedCourseId);

        try (Connection connection = DataSource.getConnection()) {
            PreparedStatement statement =
                    connection.prepareStatement("SELECT student_id, courses_id FROM students_courses WHERE id = ?");

            statement.setInt(1, studentCourseLinkId);

            final ResultSet resultSet = statement.executeQuery();
            assertFalse(resultSet.isBeforeFirst());
        }
    }
}
