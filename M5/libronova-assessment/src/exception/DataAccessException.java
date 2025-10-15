package exception;

// Checked Exception - Data Access
public class DataAccessException extends Exception {

    public DataAccessException(String message, Throwable cause) {
        super(message, cause);
    }
}