package ee.pawadeck.taskmanagement.error;



public class NotFoundException extends RuntimeException {

    public NotFoundException(String message, Object ...args) {
        super(String.format(message, args));
    }

}
