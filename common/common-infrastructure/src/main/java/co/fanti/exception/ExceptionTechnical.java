package co.fanti.exception;

public class ExceptionTechnical extends RuntimeException {

    public ExceptionTechnical(String message) {
        super(message);
    }

    public  ExceptionTechnical(String message, Exception exception) {
        super(message, exception);
    }
}
