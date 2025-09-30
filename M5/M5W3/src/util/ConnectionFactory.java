package util;

import java.sql.Connection;
import java.io.IOException;
import java.io.InputStream;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class ConnectionFactory {

    // Define DB fields
    private static final String PROPERTIES_FILE = "resources/db.properties"; // Get DB parameters
    private static final String DB_URL;
    private static final String DB_USERNAME;
    private static final String DB_PASSWORD;

    static {

        // Create connection factory
        try(InputStream input = ConnectionFactory.class.getClassLoader().getResourceAsStream(PROPERTIES_FILE)) {
        Properties properties = new Properties();

            // If properties file is not found
            if (input == null){

                throw new RuntimeException("Unable to find " + PROPERTIES_FILE);

            }

            properties.load(input);

            // Set DB parameters
            DB_URL = properties.getProperty("db.url");
            DB_USERNAME = properties.getProperty("db.username");
            DB_PASSWORD = properties.getProperty("db.password");
            String DB_DRIVER = properties.getProperty("db.driver");

            Class.forName(DB_DRIVER);

        }

        catch (IOException | ClassNotFoundException ex) {
            throw new RuntimeException("Failed to load database configuration", ex);
        }

    }

    public static Connection getConnection() throws SQLException {

        // Check if connection is successful.
        return DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD);

    }

}
