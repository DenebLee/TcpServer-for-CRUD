package kr.nanoit.decoder;

import kr.nanoit.model.message_Structure.send.IndexSend;
import kr.nanoit.model.message_Structure.send.LengthSend;

public class DecoderMessage {

    public String messageType(byte[] receive) {
        return (new String(receive, (IndexSend.INDEX_SEND_MESSAGE_TYPE), LengthSend.LENGTH_SEND_MESSAGE_TYPE)).trim();
    }

    public String messageSendTime(byte[] receive) {
        return (new String(receive, (IndexSend.INDEX_SEND_MESSAGE_SEND_TIME), LengthSend.LENGTH_SEND_MESSAGE_SEND_TIME)).trim();
    }

    public String messageState(byte[] receive) {
        return (new String(receive, (IndexSend.INDEX_SEND_MESSAGE_STATE), LengthSend.LENGTH_SEND_MESSAGE_SEND_STATE)).trim();
    }

    public String messageSender(byte[] receive) {
        return (new String(receive, (IndexSend.INDEX_SEND_MESSAGE_SENDER), LengthSend.LENGTH_SEND_MESSAGE_SEND_SENDER)).trim();
    }

    public String messageContent(byte[] receive) {
        return (new String(receive, (IndexSend.INDEX_SEND_MESSAGE_CONTENT), LengthSend.LENGTH_SEND_MESSAGE_SEND_CONTENT)).trim();
    }
}
