package kr.nanoit.model.message;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.sql.Timestamp;

@Getter
@Setter
@ToString
public class Message implements MessageService{

    private String protocol;
    private Timestamp send_time;
    private String sendstate;
    private String sender;
    private String content;

    @Override
    public String getProtocol() {
        return protocol;
    }
}
