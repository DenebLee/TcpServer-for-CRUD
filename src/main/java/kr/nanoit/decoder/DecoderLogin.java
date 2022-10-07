package kr.nanoit.decoder;


import kr.nanoit.model.message_Structure.login.IndexLogin;

public class DecoderLogin {

    public String id(byte[] byteOfReceive) {
        return (new String(byteOfReceive, (IndexLogin.COMMON_INDEX_LOGIN_ID), 100)).trim();
    }


    public String password(byte[] byteOfReceive) {
        return (new String(byteOfReceive, (IndexLogin.COMMON_INDEX_LOGIN_PASSWORD), 100)).trim();
    }

}