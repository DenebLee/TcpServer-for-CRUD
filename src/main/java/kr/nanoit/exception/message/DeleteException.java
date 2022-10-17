package kr.nanoit.exception.message;

public class DeleteException extends Exception {

    private final String reason;

    public DeleteException(String reason) {
        super(reason);
        this.reason = reason;
    }

    public String getReason() {
        return reason;
    }
}