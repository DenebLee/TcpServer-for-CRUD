package kr.nanoit.model.message;

import lombok.*;

import java.sql.Timestamp;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor


public class ReceiveMessage implements Message {
    private MessageType message_type;
    private Integer message_status ;

    private long received_id;
    private Timestamp received_time;
    private Integer sender_agent_id;
    private String to_phone_number;
    private String from_phone_number;
    private String message_content;


    @Override
    public String getMessageType() {
        return message_type.getProperty();
    }

    @Override
    public Integer getStatus() {
        return message_status;
    }

}
