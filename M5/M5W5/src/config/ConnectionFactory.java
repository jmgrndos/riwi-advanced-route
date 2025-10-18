package config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionFactory {

    // JDBC connection parameters
    private static final String URL = "jdbc:mysql://localhost:3306/gdr";
    private static final String USER = "root";
    private static final String PASSWORD = "db_LocalPass1";

    // Get a new connection
    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
}
