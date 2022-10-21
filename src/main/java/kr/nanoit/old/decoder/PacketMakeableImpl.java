package kr.nanoit.old.decoder;

import kr.nanoit.model.message.ReceiveMessageDto;
import kr.nanoit.model.message_Structure.login.IndexLogin;
import kr.nanoit.model.message_Structure.IndexHeader;
import kr.nanoit.model.message_Structure.LengthHeader;
import kr.nanoit.model.message_Structure.PacketType;
import kr.nanoit.old.util.Crypt;

import java.util.Arrays;

import static kr.nanoit.old.util.GlobalVariable.BYTE_SPACE;


public class PacketMakeableImpl implements PacketMakeable {
    Crypt crypt;


    public PacketMakeableImpl() {
        this.crypt = new Crypt();
    }


    @Override
    public byte[] send_ack(ReceiveMessageDto receiveMessageDto) {
        return new byte[0];
    }

    @Override
    public byte[] login_ack(String result) {
        byte[] data = new byte[IndexLogin.COMMON_INDEX_LOGIN_FULL_LENGTH];
        Arrays.fill(data, 0, data.length, BYTE_SPACE);
        String bodySize = Integer.toString(data.length - IndexHeader.COMMON_INDEX_HEADER_FULL_LENGTH);

        /* HEADER */
        System.arraycopy(PacketType.LOGIN.getBytes(), 0, data, LengthHeader.COMMON_LENGTH_HEADER_PACKET_TYPE_INDEX, PacketType.LOGIN.getBytes().length);
        System.arraycopy(bodySize.getBytes(), 0, data, IndexHeader.COMMON_INDEX_HEADER_BODY_LEN, bodySize.getBytes().length);

        /* BODY */
        System.arraycopy(result.getBytes(), 0, data, IndexLogin.COMMON_INDEX_LOGIN_ID, result.getBytes().length);
        return data;
    }

    @Override
    public byte[] report() {
        return new byte[0];
    }
}
