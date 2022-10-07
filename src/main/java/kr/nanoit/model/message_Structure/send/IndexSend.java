package kr.nanoit.model.message_Structure.send;

import kr.nanoit.model.message_Structure.IndexHeader;

public class IndexSend {

    public static int INDEX_SEND_MESSAGE_TYPE = IndexHeader.COMMON_INDEX_HEADER_FULL_LENGTH;

    public static int INDEX_SEND_MESSAGE_SEND_TIME = INDEX_SEND_MESSAGE_TYPE + LengthSend.LENGTH_SEND_MESSAGE_TYPE;

    public static int INDEX_SEND_MESSAGE_STATE = INDEX_SEND_MESSAGE_SEND_TIME + LengthSend.LENGTH_SEND_MESSAGE_SEND_TIME;

    public static int INDEX_SEND_MESSAGE_SENDER = INDEX_SEND_MESSAGE_STATE + LengthSend.LENGTH_SEND_MESSAGE_SEND_STATE;

    public static int INDEX_SEND_MESSAGE_CONTENT = INDEX_SEND_MESSAGE_SENDER + LengthSend.LENGTH_SEND_MESSAGE_SEND_SENDER;



}
