package app;

import config.ConnectionFactory;

import java.sql.Connection;
import java.sql.SQLException;

public class Main {

    public static void main(String[] args) {

        // Test the database connection
        try (Connection _ = ConnectionFactory.getConnection()) {
            System.out.println("Connection successful!");
        } catch (SQLException e) {
            e.printStackTrace();
        }

        // Start the main menu
        view.MainMenu.showMainMenu();

    }

}
