package ua.com.foxminded.service;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.core.metrics.ApplicationStartup;
import ua.com.foxminded.db.DataSource;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.sql.*;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class InitialDataTest {

    @Autowired
    private InitialData initialData;

    @BeforeEach
    void beforeEach() throws SQLException, IOException {
        Connection connection = DataSource.getConnection();

        Statement statement2 = connection.createStatement();
        statement2.executeUpdate(new String(Objects.requireNonNull(Thread.currentThread()
                .getContextClassLoader().getResourceAsStream("scriptDB/initial_H2.sql")).readAllBytes(),
                StandardCharsets.UTF_8));
        connection.close();
    }

    @Test
    void addGroupsToDataBase() throws SQLException, IOException {
        Connection connection = DataSource.getConnection();

        initialData.addCoursesToDataBase();
        initialData.addGroupsToDataBase();
        initialData.addStudentsToDataBase();

        PreparedStatement preparedStatement2 =
                connection.prepareStatement("SELECT * FROM groups",
                        ResultSet.TYPE_SCROLL_INSENSITIVE,
                        ResultSet.CONCUR_READ_ONLY);
        final ResultSet resultSet2 = preparedStatement2.executeQuery();
        int rows = 0;
        if (resultSet2.last()) {
            rows = resultSet2.getRow();
            resultSet2.beforeFirst();
        }

        assertEquals(11, rows);
    }

    @Test
    void addStudentsToDataBase() throws SQLException, IOException {
        Connection connection = DataSource.getConnection();

        initialData.addCoursesToDataBase();
        initialData.addGroupsToDataBase();
        initialData.addStudentsToDataBase();

        PreparedStatement preparedStatement2 =
                connection.prepareStatement("SELECT * FROM students",
                        ResultSet.TYPE_SCROLL_INSENSITIVE,
                        ResultSet.CONCUR_READ_ONLY);
        final ResultSet resultSet2 = preparedStatement2.executeQuery();
        int rows = 0;
        if (resultSet2.last()) {
            rows = resultSet2.getRow();
            resultSet2.beforeFirst();
        }

        assertEquals(200, rows);
    }

    @Test
    void addCoursesToDataBase() throws SQLException, IOException {
        Connection connection = DataSource.getConnection();

        initialData.addCoursesToDataBase();
        initialData.addGroupsToDataBase();
        initialData.addStudentsToDataBase();

        PreparedStatement preparedStatement2 =
                connection.prepareStatement("SELECT * FROM courses",
                        ResultSet.TYPE_SCROLL_INSENSITIVE,
                        ResultSet.CONCUR_READ_ONLY);
        final ResultSet resultSet2 = preparedStatement2.executeQuery();
        int rows = 0;
        if (resultSet2.last()) {
            rows = resultSet2.getRow();
            resultSet2.beforeFirst();
        }

        assertEquals(10, rows);
    }
}