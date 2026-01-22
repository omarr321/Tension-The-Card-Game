package Helper;

public class MessageWrapper {
    private Logging.LOG_TYPE type;
    private String message;

    public MessageWrapper(Logging.LOG_TYPE type, String message) {
        this.type = type;
        this.message = message;
    }

    public Logging.LOG_TYPE getType() {return this.type;}
    public String getMessage() {return this.message;}
}
