package soft.demo.exception;

public class MessageException extends Exception {

    private String message;

    public MessageException(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

}
