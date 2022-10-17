package kr.nanoit.decoder;

import kr.nanoit.model.message.MessageDto;
import kr.nanoit.model.message_Structure.login.IndexLogin;
import kr.nanoit.util.Crypt;

import java.util.Arrays;

import static kr.nanoit.model.message_Structure.IndexHeader.COMMON_INDEX_HEADER_BODY_LEN;
import static kr.nanoit.model.message_Structure.IndexHeader.COMMON_INDEX_HEADER_FULL_LENGTH;
import static kr.nanoit.model.message_Structure.LengthHeader.COMMON_LENGTH_HEADER_PACKET_TYPE_INDEX;
import static kr.nanoit.model.message_Structure.PacketType.LOGIN;
import static kr.nanoit.util.GlobalVariable.BYTE_SPACE;


public class PacketMakeableImpl implements PacketMakeable {
    Crypt crypt;


    public PacketMakeableImpl() {
        this.crypt = new Crypt();
    }


    @Override
    public byte[] send_ack(MessageDto messageDto) {
        return new byte[0];
    }

    @Override
    public byte[] login_ack(String result) {
        byte[] data = new byte[IndexLogin.COMMON_INDEX_LOGIN_FULL_LENGTH];
        Arrays.fill(data, 0, data.length, BYTE_SPACE);
        String bodySize = Integer.toString(data.length - COMMON_INDEX_HEADER_FULL_LENGTH);

        /* HEADER */
        System.arraycopy(LOGIN.getBytes(), 0, data, COMMON_LENGTH_HEADER_PACKET_TYPE_INDEX, LOGIN.getBytes().length);
        System.arraycopy(bodySize.getBytes(), 0, data, COMMON_INDEX_HEADER_BODY_LEN, bodySize.getBytes().length);

        /* BODY */
        System.arraycopy(result.getBytes(), 0, data, IndexLogin.COMMON_INDEX_LOGIN_ID, result.getBytes().length);
        return data;
    }

    @Override
    public byte[] report() {
        return new byte[0];
    }
}
