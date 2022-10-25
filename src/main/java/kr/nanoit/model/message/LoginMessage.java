package kr.nanoit.model.message;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class LoginMessage implements Message {
    private MessageType messageType;

    private String protocol;
    private String id;
    private String password;

    @Override
    public String getMessageType() {
        return messageType.getProperty();
    }

    @Override
    public Integer getStatus() {
        return null;
    }
}
