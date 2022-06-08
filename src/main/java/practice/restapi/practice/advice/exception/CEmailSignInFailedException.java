package practice.restapi.practice.advice.exception;

public class CEmailSignInFailedException extends RuntimeException {

    public CEmailSignInFailedException() {
        super();
    }

    public CEmailSignInFailedException(String message) {
        super(message);
    }

    public CEmailSignInFailedException(String message, Throwable cause) {
        super(message, cause);
    }
}
