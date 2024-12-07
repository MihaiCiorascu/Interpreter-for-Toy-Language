package exceptions;

public class IRepositoryException extends RuntimeException {
    public IRepositoryException(String message) {
        super(message);
    }
}
