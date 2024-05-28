package exceptions;

public class PrimaryKeyException extends Exception{
    public PrimaryKeyException() {
    }

    public PrimaryKeyException(String message) {
        super(message);
    }

    public PrimaryKeyException(String message, Throwable cause) {
        super(message, cause);
    }

    public PrimaryKeyException(Throwable cause) {
        super(cause);
    }
}