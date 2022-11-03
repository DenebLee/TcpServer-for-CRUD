package kr.nanoit.model.message;

import lombok.*;

import java.sql.Timestamp;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor


public class ReceiveMessage implements Message {


    private MessageType message_type; // SMS, LMS 등등 현재 SMS만 설정 client에게 받을 값
    private String protocol; // SEND, SEND_ACK , REPORT, REPORT_ACK, LOGIN, LOGIN_ACK
    private Integer message_status; // wait, selected,send DB체크용

    private long received_id; // auto increment로 불필요 -> not null이라 default 0로 사용
    private Timestamp received_time; // server측에서 DB insert할때 삽입
    private Integer sender_agent_id; // clinet에게서 받을 값
    private String to_phone_number;// clinet에게서 받을 값
    private String from_phone_number;// clinet에게서 받을 값
    private String message_content;// clinet에게서 받을 값


    @Override
    public String getMessageType() {
        return message_type.getProperty();
    }

    @Override
    public Integer getStatus() {
        return message_status;
    }

}
