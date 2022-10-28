package kr.nanoit.exception.message;

public class SendException extends Exception {

    private final String reason;

    public SendException(String reason) {
        super(reason);
        this.reason = reason;
    }

    public String getReason() {
        return reason;
    }
}