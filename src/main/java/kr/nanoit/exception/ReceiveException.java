package kr.nanoit.exception;

public class ReceiveException extends Exception {

    private final String reason;

    public ReceiveException(String reason) {
        super(reason);
        this.reason = reason;
    }

    public String getReason() {
        return reason;
    }
}