package kr.nanoit.model.decoder;

import kr.nanoit.model.message_Structure.login.IndexLogin;

public class DecoderLogin {

    public String id(byte[] receive) {
        return (new String(receive, (IndexLogin.COMMON_INDEX_LOGIN_ID), 100)).trim();
    }

    public String password(byte[] receive) {
        return (new String(receive, (IndexLogin.COMMON_INDEX_LOGIN_PASSWORD), 100)).trim();
    }
}
