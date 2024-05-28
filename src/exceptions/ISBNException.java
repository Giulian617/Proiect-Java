package exceptions;

public class ISBNException extends Exception{
    public ISBNException() {
    }

    public ISBNException(String message) {
        super(message);
    }

    public ISBNException(String message, Throwable cause) {
        super(message, cause);
    }

    public ISBNException(Throwable cause) {
        super(cause);
    }
}