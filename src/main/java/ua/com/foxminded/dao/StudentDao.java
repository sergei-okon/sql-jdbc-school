package ua.com.foxminded.dao;

import ua.com.foxminded.model.Student;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class StudentDao {

    DataSource dataSource;

    public List<Student> findById(int id) {
        List<Student> students = new ArrayList<>();

        try {
            PreparedStatement preparedStatement = DataSource.getConnection()
                    .prepareStatement("SELECT * FROM GROUPS WHERE group_id=?");

            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();

            students = groupMapper(resultSet);

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return students;
    }

    public List<Student> findAll() {
        List<Student> students = new ArrayList<>();

        try {
            Statement statement = DataSource.getConnection().createStatement();
            String SQL = "SELECT * FROM STUDENTS";

            ResultSet resultSet = statement.executeQuery(SQL);
            students = groupMapper(resultSet);

        } catch (SQLException e) {
            e.printStackTrace();
        }

        students.sort(Comparator.comparing(Student::getId));

        return students;
    }

//    public void addNew(Student student) {
//
//        try {
//            PreparedStatement preparedStatement = DataSource.getConnection()
//                    .prepareStatement("INSERT INTO GROUPS(name) VALUES (?);");
//
//            preparedStatement.setString(1, student.getName());
//
//            preparedStatement.executeUpdate();
//            System.out.println("Added new Group: " + student.getName());
//
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//    }

//    public void update(int id, Student updateStudent) {
//
//        try {
//            PreparedStatement preparedStatement = DataSource.getConnection()
//                    .prepareStatement("UPDATE students SET  =? where student_id=?;");
//
//            preparedStatement.setString(1, updateStudent.getName());
//            preparedStatement.setInt(2, id);
//
//            preparedStatement.executeUpdate();
//
//            System.out.println("Update Group: " + updateStudent.getName());
//
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//    }

    public void delete(int id) {

        try {
            System.out.println("Delete Group: " + id + this.findById(id));

            PreparedStatement preparedStatement = DataSource.getConnection()
                    .prepareStatement("DELETE FROM STUDENTS where student_id=?;");

            preparedStatement.setInt(1, id);

            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private List<Student> groupMapper(ResultSet resultSet) throws SQLException {
        List<Student> students = new ArrayList<>();

        while (resultSet.next()) {
            Student student = new Student();

            student.setId(resultSet.getInt("student_id"));
            student.setFirst_name(resultSet.getString("first_name"));
            student.setLast_name(resultSet.getString("last_name"));
            student.setGroup(resultSet.getString("group"));
//            student.setCourse1(resultSet.getString("course1"));
//            student.setCourse2(resultSet.getString("course2"));
//            student.setCourse3(resultSet.getString("course3"));

            students.add(student);
        }

        return students;
    }
}
