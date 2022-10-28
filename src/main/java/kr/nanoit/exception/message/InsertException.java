package kr.nanoit.exception.message;

public class InsertException extends Exception {

    private final String reason;

    public InsertException(String reason) {
        super(reason);
        this.reason = reason;
    }

    public String getReason() {
        return reason;
    }
}