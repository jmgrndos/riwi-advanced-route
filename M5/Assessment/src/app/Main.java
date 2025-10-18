package app;

import config.ConnectionFactory;
import controller.common.MainController;
import util.Logger;

import java.sql.Connection;
import java.sql.SQLException;

public class Main {

    public static void main(String[] args) {

        // Establish database connection
        try(Connection _ = ConnectionFactory.getConnection()){

            // Log success
            Logger.log(Logger.LogLevel.INFO, "Database connection successful");
        } catch (SQLException e){

            // Log error
            Logger.log(Logger.LogLevel.ERROR, "Database connection failed: " + e.getMessage());
            e.printStackTrace();

            // Exit application
            System.exit(1);
        }

        // Start application
        Logger.log(Logger.LogLevel.INFO, "Application started");

        // Run main controller
        MainController mainController = new MainController();
        mainController.run();

        // Log success
        Logger.log(Logger.LogLevel.INFO, "Application finished");

    }
}
