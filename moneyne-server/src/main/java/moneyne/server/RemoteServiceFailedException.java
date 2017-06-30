package moneyne.server;

/**
 * Created by magiclane on 30/06/2017.
 */
public class RemoteServiceFailedException extends RuntimeException {
    public RemoteServiceFailedException(String msg, Throwable e) {
        super(msg, e);
    }

    public RemoteServiceFailedException(String msg) {
        super(msg);
    }
}
