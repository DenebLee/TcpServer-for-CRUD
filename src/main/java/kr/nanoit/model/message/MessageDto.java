package kr.nanoit.model.message;


import lombok.*;

import java.sql.Timestamp;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor

// 메시지
public class MessageDto {

    private long id; // pk
    private Timestamp send_time;
    private String sender;
    private String content;

}
