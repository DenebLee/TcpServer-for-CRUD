package kr.nanoit.exception.message;

public class UpdateException extends Exception {

    private final String reason;

    public UpdateException(String reason) {
        super(reason);
        this.reason = reason;
    }

    public String getReason() {
        return reason;
    }
}