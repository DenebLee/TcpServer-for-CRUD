package kr.nanoit.model.message;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class LoginMessage implements MessageService {

    private String protocol;
    private String id;
    private String password;

    @Override
    public String getProtocol() {
        return protocol;
    }
}
