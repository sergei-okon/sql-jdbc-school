package ua.com.foxminded.db.dao;

import org.springframework.stereotype.Component;
import ua.com.foxminded.db.DataSource;
import ua.com.foxminded.model.Group;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Component
public class GroupDao {

    private final DataSource dataSource;

    public GroupDao(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public List<Group> findAllGroupsWithLessOrEqualsStudentCount(Integer count) {
        List<Group> groups = new ArrayList<>();
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection
                     .prepareStatement("SELECT group_id,name,amount_students FROM groups WHERE groups.amount_students>=?")) {

            preparedStatement.setInt(1, count);
            ResultSet resultSet = preparedStatement.executeQuery();
            groups = groupMapper(resultSet);

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return groups;
    }

    public Group findById(Integer id) {
        Group group = new Group();

        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection
                     .prepareStatement("SELECT group_id, name, amount_students FROM GROUPS WHERE group_id=?")) {

            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                group.setId(resultSet.getInt("group_id"));
                group.setName(resultSet.getString("name"));
                group.setAmountStudents(resultSet.getInt("amount_students"));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return group;
    }

    public void create(Group group) {

        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection
                     .prepareStatement("INSERT INTO GROUPS(name, amount_students) VALUES (?,?);")) {

            preparedStatement.setString(1, group.getName());
            preparedStatement.setInt(2, group.getAmountStudents());

            preparedStatement.executeUpdate();
            System.out.println("Added new group " + group);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void update(Integer id, Group updateGroup) {

        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection
                     .prepareStatement("UPDATE GROUPS SET name=? where group_id=?;")) {

            preparedStatement.setString(1, updateGroup.getName());
            preparedStatement.setInt(2, id);
            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void delete(Integer id) {

        try (Connection connection = dataSource.getConnection();
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
            group.setAmountStudents(resultSet.getInt("amount_students"));

            groups.add(group);
        }
        return groups;
    }
}
