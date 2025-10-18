package util;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Logger {

    // LogLevel enum defines the different levels of log messages.
    public enum LogLevel {
        INFO, WARN, ERROR
    }

    // Define file name
    private static final String LOG_FILE = "app.log";

    // Formatter for timestamp on log messages.
    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    // Get logs
    public static void log(LogLevel level, String message) {

        // Get the current timestamp.
        String timestamp = LocalDateTime.now().format(formatter);

        // Format the log message with timestamp, level, and the message itself.
        String logMessage = String.format("[%s] [%s] %s%n", timestamp, level, message);

        // Write the log message to the log file.
        try (PrintWriter writer = new PrintWriter(new FileWriter(LOG_FILE, true))) {
            writer.print(logMessage);

        } catch (IOException e) {
            // If there's an error writing to the log file, print an error to the console.
            System.err.println("Error writing to log file: " + e.getMessage());
        }
    }
}
