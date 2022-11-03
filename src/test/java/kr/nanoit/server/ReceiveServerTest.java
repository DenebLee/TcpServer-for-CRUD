package kr.nanoit.server;

import kr.nanoit.controller.SocketUtil;
import kr.nanoit.tcp.MessageDto;
import kr.nanoit.tcp.ReceiveServer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.FileReader;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketAddress;
import java.util.Properties;

import static org.assertj.core.api.Assertions.assertThat;


class ReceiveServerTest {
    private MessageDto messageDto;
    private Properties properties;

    public ReceiveServerTest() {
        messageDto = new MessageDto();
        properties = new Properties();
    }

    @BeforeEach
    void setUp() throws IOException {

        FileReader resources = new FileReader("resource.properties");
        properties.load(resources);
        try {
            ServerSocket serverSocket = new ServerSocket(Integer.parseInt(properties.getProperty("tcp.server.port")));
            SocketUtil socketUtil = new SocketUtil(serverSocket.accept());
            Thread testTread = new Thread(new ReceiveServer(socketUtil, properties));

            testTread.start();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    @DisplayName("Clinet가 서버에 접속되는지")
    void should_connect_to_server() throws IOException {
        // given
        Socket socket = new Socket();
        SocketAddress socketAddress = new InetSocketAddress(properties.getProperty("tcp.host"), Integer.parseInt(properties.getProperty("tcp.server.port")));

        // when
        socket.connect(socketAddress);


        // then
        assertThat(ReceiveServer.status).isTrue();
    }

//    private byte[] makeSendPacket() {
//        byte[] data = new byte[IndexSend.INDEX_SEND_FULL];
//
//        Arrays.fill(data, 0, data.length, BYTE_SPACE);
//        String bodySize = Integer.toString(data.length - IndexHeader.COMMON_INDEX_HEADER_FULL_LENGTH);
//
//        /*
//         * Header
//         */
//        System.arraycopy(PacketType.SEND.getBytes(), 0, data, LengthHeader.COMMON_LENGTH_HEADER_PACKET_TYPE_INDEX, PacketType.SEND.getBytes().length);
//        System.arraycopy(bodySize.getBytes(), 0, data, IndexHeader.COMMON_INDEX_HEADER_BODY_LEN, bodySize.getBytes().length);
//
//        /*
//         * Body
//         */
//        System.arraycopy(messageDto.getMessageType().getBytes(), 0, data, IndexSend.INDEX_MESSAGE_TYPE, messageDto.getMessageType().getBytes().length);
//        System.arraycopy(messageDto.getSender_agent_id().getBytes(), 0, data, IndexSend.INDEX_SENDER_AGENT_ID, messageDto.getSender_agent_id().getBytes().length);
//        System.arraycopy(messageDto.getFrom_phone_number().getBytes(), 0, data, IndexSend.INDEX_FROM_PHONE_NUMBER, messageDto.getFrom_phone_number().getBytes().length);
//        System.arraycopy(messageDto.getTo_phone_number().getBytes(), 0, data, IndexSend.INDEX_TO_PHONE_NUMBER, messageDto.getFrom_phone_number().getBytes().length);
//        System.arraycopy(messageDto.getMessage_content().getBytes(), 0, data, IndexSend.INDEX_MESSAGE_CONTENT, messageDto.getMessage_content().getBytes().length);
//
//        return data;
//    }


    private final byte BYTE_SPACE = ' ';

}
