package kr.nanoit.model.message;


import lombok.*;

import java.sql.Timestamp;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor

// 메시지
public class ReceiveMessageDto {

    private long id; // pk
    private MessageType message_type;
    private Timestamp received_time;
    private Integer sender_agent_id;
    private String to_phone_number;
    private String from_phone_number;
    private String message_content;
}
