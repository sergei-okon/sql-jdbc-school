package ua.com.foxminded.db.dao;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ua.com.foxminded.db.DataSource;
import ua.com.foxminded.model.Group;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
class GroupDaoTest extends TestUtils {

    @Autowired
    private GroupDao groupDao;

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
    void findAllGroupsWithLessOrEqualsStudentCount() {
    }

    @Test
    void findById() throws SQLException {
        String expectedName = "AB-33";
        Integer expectedAmountStudents = 2;

        Integer savedGroupId = createGroup(expectedName, expectedAmountStudents);

        Group actualGroup = groupDao.findById(savedGroupId);

        assertEquals(savedGroupId, actualGroup.getId());
        assertEquals(expectedName, actualGroup.getName());
        assertEquals(expectedAmountStudents, actualGroup.getAmountStudents());
    }

    @Test
    void addNew() throws SQLException {
        String expectedName = "AB-33";
        Integer expectedAmountStudents = 2;

        Integer savedGroupId = createGroup(expectedName, expectedAmountStudents);

        Group actualGroup = groupDao.findById(savedGroupId);

        assertNotNull(actualGroup);
        assertEquals(savedGroupId, actualGroup.getId());
        assertEquals(expectedName, actualGroup.getName());
        assertEquals(expectedAmountStudents, actualGroup.getAmountStudents());
    }
}