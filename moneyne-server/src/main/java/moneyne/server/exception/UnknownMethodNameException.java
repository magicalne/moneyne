package moneyne.server.exception;

/**
 * Created by magiclane on 30/06/2017.
 */
public class UnknownMethodNameException extends MonenyeExecutionException {
    public UnknownMethodNameException(String message, Throwable cause) {
        super(message, cause);
    }

    public UnknownMethodNameException(String msg) {
        super(msg);
    }
}
