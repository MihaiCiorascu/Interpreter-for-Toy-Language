package exceptions;

public class IControllerException extends RuntimeException {
    public IControllerException(String message) {
        super(message);
    }
}
