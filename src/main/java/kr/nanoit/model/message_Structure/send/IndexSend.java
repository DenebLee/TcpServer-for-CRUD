package kr.nanoit.model.message_Structure.send;

import kr.nanoit.model.message_Structure.IndexHeader;

/**
 * messge_type, sender_agent_id, to_phone_number, from_phone_number, message_content
 */
public class IndexSend {
    public static int INDEX_MESSAGE_TYPE = IndexHeader.COMMON_INDEX_HEADER_FULL_LENGTH;

    public static int INDEX_SENDER_AGENT_ID = INDEX_MESSAGE_TYPE + LengthSend.LENGTH_SEND_MESSAGE_TYPE;

    public static int INDEX_TO_PHONE_NUMBER = INDEX_SENDER_AGENT_ID + LengthSend.LENGTH_SENDER_AGENT_ID;

    public static int INDEX_FROM_PHONE_NUMBER = INDEX_TO_PHONE_NUMBER + LengthSend.LENGTH_TO_PHONE_NUMBER;

    public static int INDEX_MESSAGE_CONTENT = INDEX_FROM_PHONE_NUMBER + LengthSend.LENGTH_FROM_PHONE_NUMBER;

    public static int INDEX_SEND_FULL = INDEX_MESSAGE_CONTENT + LengthSend.LENGTH_MESSAGE_CONTENT;
}
