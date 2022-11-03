package kr.nanoit.model.message;

import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor

public class SendMessage implements Message {
    private MessageType message_type;
    private String protocol;
    private Integer message_status;

    private long send_id;
    private long received_id;
    private String result;

    @Override
    public String getMessageType() {
        return message_type.getProperty();
    }

    @Override
    public Integer getStatus() {
        return message_status;
    }
}
