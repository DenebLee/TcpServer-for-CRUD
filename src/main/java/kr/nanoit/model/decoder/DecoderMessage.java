package kr.nanoit.model.decoder;

import kr.nanoit.model.message.MessageType;
import kr.nanoit.model.message_Structure.send.IndexSend;
import kr.nanoit.model.message_Structure.send.LengthSend;

public class DecoderMessage {

    /**
     * @param receiveByte the byte of receive
     */

    public MessageType messageType(byte[] receiveByte) {
        String test = new String(receiveByte, (IndexSend.INDEX_MESSAGE_TYPE), LengthSend.LENGTH_SEND_MESSAGE_TYPE);
        return MessageType.fromPropertyName(test);
    }

    public Integer sender_agent_id(byte[] receiveByte) {
        return Integer.parseInt(new String(receiveByte, (IndexSend.INDEX_SENDER_AGENT_ID), LengthSend.LENGTH_SENDER_AGENT_ID).trim());
    }

    public String to_phone_number(byte[] receiveByte) {
        return (new String(receiveByte, (IndexSend.INDEX_TO_PHONE_NUMBER), LengthSend.LENGTH_TO_PHONE_NUMBER));
    }

    public String from_phone_number(byte[] receiveByte) {
        return (new String(receiveByte, (IndexSend.INDEX_FROM_PHONE_NUMBER), LengthSend.LENGTH_FROM_PHONE_NUMBER));
    }

    public String message_content(byte[] receiveByte) {
        return (new String(receiveByte, (IndexSend.INDEX_MESSAGE_CONTENT), LengthSend.LENGTH_MESSAGE_CONTENT));
    }
}
