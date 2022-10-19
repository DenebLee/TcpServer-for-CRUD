package kr.nanoit.old.exception.message;

public class SelectException extends Exception {

    private final String reason;

    public SelectException(String reason) {
        super(reason);
        this.reason = reason;
    }

    public String getReason() {
        return reason;
    }
}