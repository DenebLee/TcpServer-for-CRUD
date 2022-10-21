package kr.nanoit.model.message;

import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class SendMessageDto {

    private long id;
    private long received_id;
    private Integer status;
    private String result;

}
