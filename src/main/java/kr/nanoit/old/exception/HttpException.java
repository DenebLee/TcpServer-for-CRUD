package kr.nanoit.old.exception;

public class HttpException extends Exception {

    private final String reason;

    public HttpException(String reason) {
        super(reason);
        this.reason = reason;
    }

    public String getReason() {
        return reason;
    }
}