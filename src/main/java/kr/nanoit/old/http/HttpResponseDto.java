package kr.nanoit.old.http;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class HttpResponseDto {
    private String host;
    private String port;
}
