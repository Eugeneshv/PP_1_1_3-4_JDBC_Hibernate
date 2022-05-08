package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

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
        try (Statement statement = connection.createStatement()){
            String SQL = "CREATE TABLE IF NOT EXISTS user (id int NOT NULL auto_increment, " +
                    "name varchar(64), lastname varchar(64), age int, PRIMARY KEY (id))";
            statement.execute(SQL);

        } catch (SQLException e) {
            e.getStackTrace();
        }
    }

    public void dropUsersTable() {
        try (Statement statement = connection.createStatement()){
            String SQL = "DROP TABLE if EXISTS user";
            statement.execute(SQL);
        } catch (SQLException e) {
            e.getStackTrace();
        }
    }

    public void saveUser(String name, String lastName, byte age) {
        try (Statement statement = connection.createStatement()){
            String SQL = new StringBuilder().append("insert into user (name, lastname, age) values ('")
                    .append(name).append("', '").append(lastName).append("', ")
                    .append((int) age).append(")").toString();
            statement.execute(SQL);
        } catch (SQLException e) {
            e.getStackTrace();
        }
    }

    public void removeUserById(long id) {
        try (Statement statement = connection.createStatement()){
            String SQL = new StringBuilder().append("DELETE FROM User WHERE id=").append(id).toString();
            statement.execute(SQL);
        } catch (SQLException e) {
            e.getStackTrace();
        }
    }

    public List<User> getAllUsers() {
        String SQL = "SELECT id, name, lastname, age FROM User";
        List<User> result = new ArrayList<>();
        try (ResultSet rs = connection.createStatement().executeQuery(SQL)){
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
        try {
            Statement statement = connection.createStatement();
            String SQL = "TRUNCATE user";
            statement.execute(SQL);
        } catch (SQLException e) {
            e.getStackTrace();
        }
    }
}
