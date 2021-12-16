package ua.com.foxminded.dao;

import ua.com.foxminded.model.Group;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class GroupDao {

    public List<Group> findAllGroupsWithLessOrEqualsStudentCount(int count) {
        List<Group> groups = new ArrayList<>();
        try (Connection connection = DataSource.getConnection();
             PreparedStatement preparedStatement = connection
                     .prepareStatement("SELECT group_id,name,\"amountStudents\" FROM groups WHERE groups.\"amountStudents\">=?")) {

            preparedStatement.setInt(1, count);
            ResultSet resultSet = preparedStatement.executeQuery();
            groups = groupMapper(resultSet);

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return groups;
    }

    public List<Group> findById(int id) {
        List<Group> groups = new ArrayList<>();

        try (Connection connection = DataSource.getConnection();
             PreparedStatement preparedStatement = connection
                     .prepareStatement("SELECT group_id, name FROM GROUPS WHERE group_id=?")) {

            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();

            groups = groupMapper(resultSet);

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return groups;
    }

    public List<Group> findAll() {
        List<Group> groups = new ArrayList<>();

        try (Statement statement = DataSource.getConnection().createStatement()) {

            ResultSet resultSet = statement.executeQuery("SELECT * FROM GROUPS");
            groups = groupMapper(resultSet);

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return groups;
    }

    public void addNew(Group group) {

        try (Connection connection = DataSource.getConnection();
             PreparedStatement preparedStatement = connection
                     .prepareStatement("INSERT INTO GROUPS(name, \"amountStudents\") VALUES (?,?);")) {

            preparedStatement.setString(1, group.getName());
            preparedStatement.setInt(2, group.getAmountStudents());

            preparedStatement.executeUpdate();
            System.out.println("Added new group " + group);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void update(int id, Group updateGroup) {

        try (Connection connection = DataSource.getConnection();
             PreparedStatement preparedStatement = connection
                     .prepareStatement("UPDATE GROUPS SET name=? where group_id=?;")) {

            preparedStatement.setString(1, updateGroup.getName());
            preparedStatement.setInt(2, id);
            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void delete(int id) {

        try (Connection connection = DataSource.getConnection();
             PreparedStatement preparedStatement = connection
                     .prepareStatement("DELETE FROM GROUPS where group_id=?;")) {

            preparedStatement.setInt(1, id);

            preparedStatement.executeUpdate();

            System.out.println("Delete Group: " + id + this.findById(id));

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private List<Group> groupMapper(ResultSet resultSet) throws SQLException {
        List<Group> groups = new ArrayList<>();

        while (resultSet.next()) {
            Group group = new Group();

            group.setId(resultSet.getInt("group_id"));
            group.setName(resultSet.getString("name"));
            group.setAmountStudents(resultSet.getInt("amountStudents"));

            groups.add(group);
        }
        return groups;
    }
}
