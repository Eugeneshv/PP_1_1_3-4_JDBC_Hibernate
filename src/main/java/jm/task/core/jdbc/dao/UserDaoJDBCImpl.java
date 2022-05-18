package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;


public class UserDaoJDBCImpl implements UserDao {

    private static Logger log = Logger.getLogger(UserDaoJDBCImpl.class.getName());

    public UserDaoJDBCImpl() {

    }

    public void createUsersTable() {
        String SQL = "CREATE TABLE IF NOT EXISTS user (id int NOT NULL auto_increment, " +
                "name varchar(64), lastname varchar(64), age int, PRIMARY KEY (id))";
        try (Connection connection = Util.getConnection();
             PreparedStatement statement = connection.prepareStatement(SQL)) {
            statement.executeUpdate();
        } catch (SQLException e) {
            log.warning("Cannot create Table " + e.getStackTrace());
        }
    }

    public void dropUsersTable() {
        String SQL = "DROP TABLE if EXISTS user";
        try (Connection connection = Util.getConnection();
             PreparedStatement statement = connection.prepareStatement(SQL)) {
            statement.executeUpdate();
        } catch (SQLException e) {
            log.warning("Cannot drop Table " + e.getStackTrace());
        }
    }

    public void saveUser(String name, String lastName, byte age) {
        String SQL = "INSERT INTO user (name, lastname, age) VALUES (?, ?, ?)";
        try (Connection connection = Util.getConnection();
             PreparedStatement statement = connection.prepareStatement(SQL)) {
            statement.setString(1, name);
            statement.setString(2, lastName);
            statement.setInt(3, age);
            statement.executeUpdate();
        } catch (SQLException e) {
            log.warning("Cannot save User " + e.getStackTrace());
        }
    }

    public void removeUserById(long id) {
        String SQL = "DELETE FROM User WHERE id=?";
        try (Connection connection = Util.getConnection();
             PreparedStatement statement = connection.prepareStatement(SQL)) {
            statement.setLong(1, id);
            statement.executeUpdate();
        } catch (SQLException e) {
            log.warning("Cannot remove User " + e.getStackTrace());
        }
    }

    public List<User> getAllUsers() {
        String SQL = "SELECT id, name, lastname, age FROM user";
        List<User> result = new ArrayList<>();
        try (Connection connection = Util.getConnection();
             PreparedStatement statement = connection.prepareStatement(SQL);
             ResultSet rs = statement.executeQuery()) {
            while (rs.next()) {
                User user = new User();
                user.setId(rs.getLong("id"));
                user.setName(rs.getString("name"));
                user.setLastName(rs.getString("lastname"));
                user.setAge(rs.getByte("age"));
                result.add(user);
            }
        } catch (SQLException e) {
            log.warning("Cannot get All Users " + e.getStackTrace());
        }
        finally {
            return result;
        }
    }

    public void cleanUsersTable() {
        String SQL = "TRUNCATE user";
        try (Connection connection = Util.getConnection();
             PreparedStatement statement = connection.prepareStatement(SQL)) {
            statement.executeUpdate();
        } catch (SQLException e) {
            log.warning("Cannot clean Users Table " + e.getStackTrace());
        }
    }
}
