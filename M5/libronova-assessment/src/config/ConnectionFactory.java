package config;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class ConnectionFactory {

    // Holds the database connection details from the properties file.
    private static final Properties properties = new Properties();

    // Loads the db.properties file when the class is initialized.
    static {

        // Try-with-resources to ensure the input stream is closed.
        try (InputStream input = ConnectionFactory.class.getClassLoader().getResourceAsStream("config/db.properties")) {

            // If the properties file is not found, throw an exception.
            if (input == null) {
                throw new FileNotFoundException("Unable to find db.properties");
            }
            // Load the properties from the input stream.
            properties.load(input);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // JDBC connection parameters.
    private static final String URL = properties.getProperty("db.url");
    private static final String USER = properties.getProperty("db.user");
    private static final String PASSWORD = properties.getProperty("db.password");

    // Establishes and returns a connection to the database.
    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
}
