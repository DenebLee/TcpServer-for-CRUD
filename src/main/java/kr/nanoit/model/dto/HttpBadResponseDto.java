package kr.nanoit.model.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class HttpBadResponseDto {
    private String timestamp;
    private int code;
    private String error;
    private String message;
}
