package ua.com.foxminded.db.dao;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import ua.com.foxminded.db.DataSource;
import ua.com.foxminded.model.Course;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class CourseDaoTest extends TestUtils {
    private final CourseDao courseDao = new CourseDao();

    @BeforeAll
    static void setUp() throws SQLException, IOException {
        Connection connection = DataSource.getConnection();
        Statement statement = connection.createStatement();
        statement.executeUpdate(new String(Objects.requireNonNull(Thread.currentThread()
                .getContextClassLoader().getResourceAsStream("scriptDB/initial_H2.sql")).readAllBytes(),
                StandardCharsets.UTF_8));
        connection.close();
    }

    @Test
    void findByName() throws SQLException {
        String expectedName = "Math";
        String expectedDescription = "Mathematics";

        int savedCourseId = createCourse(expectedName, expectedDescription);

        Course actualCourse = courseDao.findByName(expectedName);

        assertEquals(expectedName, actualCourse.getName());
        assertEquals(expectedDescription, actualCourse.getDescription());
        assertEquals(savedCourseId, actualCourse.getId());
    }

    @Test
    void findById() throws SQLException {
        String expectedName = "Math";
        String expectedDescription = "Mathematics";

        int savedCourseId = createCourse(expectedName, expectedDescription);

        Course actualCourse = courseDao.findById(savedCourseId);

        assertEquals(expectedName, actualCourse.getName());
        assertEquals(expectedDescription, actualCourse.getDescription());
        assertEquals(savedCourseId, actualCourse.getId());
    }

    @Test
    void addNew() throws SQLException {
        String expectedName = "Math";
        String expectedDescription = "Mathematics";

        int savedCourseId = createCourse(expectedName, expectedDescription);

        Course actualCourse = courseDao.findById(savedCourseId);

        assertNotNull(actualCourse);
        assertEquals(expectedName, actualCourse.getName());
        assertEquals(expectedDescription, actualCourse.getDescription());
        assertEquals(savedCourseId, actualCourse.getId());
    }
}