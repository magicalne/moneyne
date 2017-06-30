package moneyne.server.exception;

/**
 * Created by magiclane on 30/06/2017.
 */
public class MonenyeExecutionException extends RuntimeException {
    public MonenyeExecutionException(String message) {
        super(message);
    }

    public MonenyeExecutionException(String message, Throwable cause) {
        super(message, cause);
    }
}
