package kr.nanoit.model.message;

import kr.nanoit.model.message_Structure.PacketType;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class LoginMessage implements Message {
    private MessageType messageType;

    private PacketType packetType;
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
