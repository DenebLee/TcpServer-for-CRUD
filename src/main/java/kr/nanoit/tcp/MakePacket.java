package kr.nanoit.tcp;

import kr.nanoit.model.message_Structure.IndexHeader;
import kr.nanoit.model.message_Structure.LengthHeader;
import kr.nanoit.model.message_Structure.PacketType;
import kr.nanoit.model.message_Structure.send.IndexSend;

import java.util.Arrays;

public class MakePacket {
    public byte[] makeSendPacket(MessageDto messageDto) {
        byte[] data = new byte[IndexSend.INDEX_SEND_FULL];

        Arrays.fill(data, 0, data.length, BYTE_SPACE);
        String bodySize = Integer.toString(data.length - IndexHeader.COMMON_INDEX_HEADER_FULL_LENGTH);

        /*
         * Header
         */
        System.arraycopy(PacketType.SEND.getBytes(), 0, data, LengthHeader.COMMON_LENGTH_HEADER_PACKET_TYPE_INDEX, PacketType.SEND.getBytes().length);
        System.arraycopy(bodySize.getBytes(), 0, data, IndexHeader.COMMON_INDEX_HEADER_BODY_LEN, bodySize.getBytes().length);

        /*
         * Body
         */
        System.arraycopy(messageDto.getSender_agent_id().getBytes(), 0, data, IndexSend.INDEX_SENDER_AGENT_ID, messageDto.getSender_agent_id().getBytes().length);
        System.arraycopy(messageDto.getFrom_phone_number().getBytes(), 0, data, IndexSend.INDEX_FROM_PHONE_NUMBER, messageDto.getFrom_phone_number().getBytes().length);
        System.arraycopy(messageDto.getTo_phone_number().getBytes(), 0, data, IndexSend.INDEX_TO_PHONE_NUMBER, messageDto.getFrom_phone_number().getBytes().length);
        System.arraycopy(messageDto.getMessage_content().getBytes(), 0, data, IndexSend.INDEX_MESSAGE_CONTENT, messageDto.getMessage_content().getBytes().length);

        return data;
    }

    public static final byte BYTE_SPACE = ' ';
}
