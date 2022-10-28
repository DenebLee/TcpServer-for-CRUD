package kr.nanoit.exception;

public class DecryptException extends Exception {

    private final String reason;

    public DecryptException(String reason) {
        super(reason);
        this.reason = reason;
    }

    public String getReason() {
        return reason;
    }
}