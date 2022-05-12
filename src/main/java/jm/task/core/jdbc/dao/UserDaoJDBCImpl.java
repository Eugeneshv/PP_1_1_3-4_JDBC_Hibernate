package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import static jm.task.core.jdbc.util.Util.connection;

public class UserDaoJDBCImpl implements UserDao {
    public UserDaoJDBCImpl() {

    }

    public void createUsersTable() {
        String SQL = "CREATE TABLE IF NOT EXISTS user (id int NOT NULL auto_increment, " +
                "name varchar(64), lastname varchar(64), age int, PRIMARY KEY (id))";
        try (PreparedStatement statement = connection.prepareStatement(SQL)){
            statement.executeUpdate();
        } catch (SQLException e) {
            e.getStackTrace();
        }
    }

    public void dropUsersTable() {
        String SQL = "DROP TABLE if EXISTS user";
        try (PreparedStatement statement = connection.prepareStatement(SQL)){
            statement.executeUpdate();
        } catch (SQLException e) {
            e.getStackTrace();
        }
    }

    public void saveUser(String name, String lastName, byte age) {
        String SQL = "INSERT INTO user (name, lastname, age) VALUES (?, ?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(SQL)){
            statement.setString(1, name);
            statement.setString(2, lastName);
            statement.setInt(3, age);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.getStackTrace();
        }
    }

    public void removeUserById(long id) {
        String SQL = "DELETE FROM User WHERE id=?";
        try (PreparedStatement statement = connection.prepareStatement(SQL)){
            statement.setLong(1, id);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.getStackTrace();
        }
    }

    public List<User> getAllUsers() {
        String SQL = "SELECT id, name, lastname, age FROM user";
        List<User> result = new ArrayList<>();
        try (ResultSet rs = connection.prepareStatement(SQL).executeQuery()){
            while (rs.next()) {
                long id = (long) rs.getInt("id");
                String name = rs.getString("name");
                String lastname = rs.getString("lastname");
                byte age = (byte) rs.getInt("age");
                User user = new User();
                user.setId(id);
                user.setName(name);
                user.setLastName(lastname);
                user.setAge(age);
                result.add(user);
            }
        } catch (SQLException e) {
            e.getStackTrace();
        }
        finally {
            return result;
        }
    }

    public void cleanUsersTable() {
        String SQL = "TRUNCATE user";
        try {
            PreparedStatement statement = connection.prepareStatement(SQL);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.getStackTrace();
        }
    }
}
