package kr.nanoit.controller;

import kr.nanoit.model.message.MessageService;
import kr.nanoit.model.message_Structure.IndexHeader;
import kr.nanoit.model.message_Structure.LengthHeader;
import kr.nanoit.model.message_Structure.PacketType;
import lombok.Getter;
import lombok.Setter;

import java.io.IOException;
import java.net.Socket;
import java.util.concurrent.LinkedBlockingQueue;

public class SocketUtil extends SocketConfig {

    private final Object receiveLock = new Object();
    @Getter
    @Setter
    private String id;
    @Getter
    @Setter
    private String password;
    @Getter
    LinkedBlockingQueue<MessageService> queue_for_Send;

    @Getter
    LinkedBlockingQueue<MessageService> queue_for_Receive;

    @Getter
    LinkedBlockingQueue<MessageService> queue_for_Report;
    @Getter
    @Setter
    private String packet_login_id;
    @Getter
    @Setter
    private String packet_login_password;

    public SocketUtil(Socket socket) throws IOException {
        super();
        setSocket(socket);
        queue_for_Send = new LinkedBlockingQueue<>();
        queue_for_Receive = new LinkedBlockingQueue<>();
        queue_for_Report = new LinkedBlockingQueue<>();
    }


    public byte[] receiveByte() throws Exception {
        synchronized (receiveLock) {
            byte[] header;
            byte[] body;
            header = read(IndexHeader.COMMON_INDEX_HEADER_FULL_LENGTH);
            if (header == null)
                return null;
            String bodyLen = new String(header, IndexHeader.COMMON_INDEX_HEADER_BODY_LEN, LengthHeader.COMMON_LENGTH_HEADER_BODY_LEN).trim();

            byte[] receiveData; // byte[] 선언
            if (bodyLen != null && !bodyLen.equals("")) {
                int bodyLength = Integer.parseInt(bodyLen); //
                receiveData = new byte[header.length + bodyLength];
                System.arraycopy(header, 0, receiveData, 0, header.length);

                if (bodyLength > 0) {
                    body = read(bodyLength);
                    if (body == null)
                        return null;

                    System.arraycopy(body, 0, receiveData, header.length, body.length);
                }
            } else {
                receiveData = new byte[header.length];
                System.arraycopy(header, 0, receiveData, 0, header.length);
            }
            return receiveData;
        }

    }

    public PacketType getPacketType(byte[] byteOfReceiveData) throws Exception {
        return PacketType.fromPropertyName(new String(byteOfReceiveData, LengthHeader.COMMON_LENGTH_HEADER_PACKET_TYPE_INDEX, LengthHeader.COMMON_LENGTH_HEADER_PACKET_TYPE).trim());
    }
}
