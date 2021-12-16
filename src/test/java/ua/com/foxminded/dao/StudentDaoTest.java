package ua.com.foxminded.dao;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ua.com.foxminded.model.Student;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;

class StudentDaoTest {
    StudentDao studentDao;

    @BeforeEach
    void setUp() {
        studentDao = new StudentDao();
    }

    @Test
    void findById() throws SQLException {
        Connection conn = DriverManager.getConnection("jdbc:h2:mem:test", "", "");

        Student actual = new Student(1, "Callum", "Mcfarland", 1);

        Student expected = studentDao.findById(1);
        System.out.println("--------------------------------------");

        System.out.println(expected.getId());
        System.out.println(expected);

        System.out.println(actual.getId());
        System.out.println("--------------------------------------");

        assertEquals(expected.getId(), actual.getId());
        assertEquals(expected.getFirstName(), actual.getFirstName());
        assertEquals(expected.getLastName(), actual.getLastName());
        assertEquals(expected.getGroupId(), actual.getGroupId());

    }
}