package kr.nanoit.model.message_Structure.send;

import kr.nanoit.model.message_Structure.IndexHeader;
import org.checkerframework.checker.units.qual.Length;

public class IndexSend {

    public static int INDEX_SEND_MESSAGE_TYPE = IndexHeader.COMMON_INDEX_HEADER_FULL_LENGTH;
    public static int INDEX_SEND_MESSAGE_ID = INDEX_SEND_MESSAGE_TYPE + LengthSend.LENGTH_SEND_MESSAGE_TYPE;
    public static int INDEX_SEND_MESSAGE_SEND_TIME = INDEX_SEND_MESSAGE_ID + LengthSend.LENGTH_SEND_MESSAGE_ID;
    public static int INDEX_SEND_MESSAGE_SENDER = INDEX_SEND_MESSAGE_SEND_TIME + LengthSend.LENGTH_SEND_MESSAGE_SEND_TIME;
    public static int INDEX_SEND_MESSAGE_CONTENT = INDEX_SEND_MESSAGE_SENDER + LengthSend.LENGTH_SEND_MESSAGE_SEND_SENDER;
    public static int INDEX_SEND_FULL = INDEX_SEND_MESSAGE_CONTENT + LengthSend.LENGTH_SEND_MESSAGE_SEND_CONTENT;

}
