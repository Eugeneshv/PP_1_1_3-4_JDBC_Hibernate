package jm.task.core.jdbc.util;

import net.bytebuddy.asm.Advice;

import java.sql.*;

import static org.eclipse.aether.repository.AuthenticationContext.PASSWORD;
import static org.hibernate.cfg.AvailableSettings.URL;
import static org.hibernate.cfg.AvailableSettings.USER;

public class Util {
    private static final String URL = "jdbc:mysql://localhost:3306/mydbtest";
    private static final String USER = "root";
    private static final String PASSWORD = "MySQLpassword";

    public static Connection getConnection(){
        Connection connection = null;
        try {
            connection = DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (SQLException e) {
            e.getStackTrace();
            throw new RuntimeException(e);
        } finally {
            return connection;
        }
    }




}
