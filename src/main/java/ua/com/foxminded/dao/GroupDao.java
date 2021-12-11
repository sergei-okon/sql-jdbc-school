package ua.com.foxminded.dao;

import ua.com.foxminded.model.Group;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class GroupDao {

    DataSource dataSource;

    public List<Group> findById(int id) {
        List<Group> groups = new ArrayList<>();

        try {
            PreparedStatement preparedStatement = DataSource.getConnection()
                    .prepareStatement("SELECT * FROM GROUPS WHERE group_id=?");

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

        try {
            Statement statement = DataSource.getConnection().createStatement();
            String SQL = "SELECT * FROM GROUPS";

            ResultSet resultSet = statement.executeQuery(SQL);
            groups = groupMapper(resultSet);

        } catch (SQLException e) {
            e.printStackTrace();
        }

        groups.sort(Comparator.comparing(Group::getId));

        return groups;
    }

    public void addNew(Group group) {

        try {
            PreparedStatement preparedStatement = DataSource.getConnection()
                    .prepareStatement("INSERT INTO GROUPS(name) VALUES (?);");

            preparedStatement.setString(1, group.getName());

            preparedStatement.executeUpdate();
            System.out.println("Added new Group: " + group.getName());

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void update(int id, Group updateGroup) {

        try {
            PreparedStatement preparedStatement = DataSource.getConnection()
                    .prepareStatement("UPDATE GROUPS SET name=? where group_id=?;");

            preparedStatement.setString(1, updateGroup.getName());
            preparedStatement.setInt(2, id);
            preparedStatement.executeUpdate();

            System.out.println("Update Group: " + updateGroup.getName());

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void delete(int id) {

        try {
            System.out.println("Delete Group: " + id + this.findById(id));

            PreparedStatement preparedStatement = DataSource.getConnection()
                    .prepareStatement("DELETE FROM GROUPS where group_id=?;");

            preparedStatement.setInt(1, id);

            preparedStatement.executeUpdate();

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

            groups.add(group);
        }

        return groups;
    }
}
