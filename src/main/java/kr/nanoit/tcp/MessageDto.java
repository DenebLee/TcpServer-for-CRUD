package kr.nanoit.tcp;

import kr.nanoit.model.message.Message;
import kr.nanoit.model.message.MessageType;
import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor

public class MessageDto implements Message {

    private MessageType message_type;
    private String sender_agent_id;
    private String to_phone_number;
    private String from_phone_number;
    private String message_content;

    @Override
    public String getMessageType() {
        return message_type.getProperty();
    }

    @Override
    public Integer getStatus() {
        return null;
    }
}
