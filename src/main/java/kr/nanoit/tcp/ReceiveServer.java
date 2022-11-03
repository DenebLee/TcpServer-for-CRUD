package kr.nanoit.tcp;

import kr.nanoit.util.Crypt;
import kr.nanoit.controller.SocketUtil;
import kr.nanoit.exception.message.InsertException;
import kr.nanoit.exception.message.SelectException;
import kr.nanoit.model.decoder.DecoderLogin;
import kr.nanoit.model.decoder.DecoderMessage;
import kr.nanoit.model.message.LoginMessage;
import kr.nanoit.model.message.MessageType;
import kr.nanoit.model.message.ReceiveMessage;
import kr.nanoit.repository.ReceivedMessageRepository;
import kr.nanoit.service.ReceivedMessageService;
import kr.nanoit.service.ReceivedMessageServiceImpl;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.util.Arrays;
import java.util.Properties;


@Slf4j
public class ReceiveServer implements Runnable {

    @Getter
    public static boolean status;
    private SocketUtil socketUtil;
    private final DecoderLogin decoderLogin;
    private ReceivedMessageRepository receivedMessageRepository;
    private ReceivedMessageService receivedMessageService;
    private DecoderMessage decoderMessage;

    private final Crypt crypt;


    public ReceiveServer(SocketUtil socketUtil, Properties prop) throws IOException {
        receivedMessageRepository = ReceivedMessageRepository.createReceiveRepository(prop);
        receivedMessageService = new ReceivedMessageServiceImpl(receivedMessageRepository);
        this.socketUtil = socketUtil;
        decoderLogin = new DecoderLogin();
        decoderMessage = new DecoderMessage();
        crypt = new Crypt();
    }

    @Override
    public void run() {
        if (socketUtil.getSocket().isConnected()) {
            log.info("RECEIVE SERVER Client Connected !!!!! {}", socketUtil.getSocket().getInetAddress());
        }
        try {
            while (true) {
                byte[] receiveByte = socketUtil.receiveByte();
                System.out.println(Arrays.toString(receiveByte));
                if (receiveByte != null) {
                    System.out.println(socketUtil.getPacketType(receiveByte).toString());
                    switch (socketUtil.getPacketType(receiveByte)) {
                        case SEND:
                            decode_login(receiveByte);
                            break;
                        case LOGIN:
                            decode_send(receiveByte);
                            break;
                    }
                }
            }
        } catch (Exception e) {
            socketUtil.socketClose();
            e.printStackTrace();
        }
    }

    public void decode_login(byte[] receiveBytes) {
        LoginMessage loginMessage = new LoginMessage();

        loginMessage.setProtocol("LOGIN_ACK");
        loginMessage.setId(decoderLogin.id(receiveBytes));
        loginMessage.setPassword(decoderLogin.id(receiveBytes));
        loginMessage.setMessageType(MessageType.LOGIN);

        // 접속하고자 하는 유저의 Login_Packet 검증
    }

    public void decode_send(byte[] recevedBytes) throws InsertException, InvalidAlgorithmParameterException, IllegalBlockSizeException, BadPaddingException, InvalidKeyException {
        try {
            if (recevedBytes != null) {
                ReceiveMessage receiveMessage = new ReceiveMessage();
                receiveMessage.setMessage_type(decoderMessage.messageType(recevedBytes));
                receiveMessage.setProtocol("SEND");
                receiveMessage.setReceived_id(0);
                receiveMessage.setFrom_phone_number(decoderMessage.from_phone_number(recevedBytes)); // decrypt
                receiveMessage.setTo_phone_number(decoderMessage.to_phone_number(recevedBytes)); // decrypt
                receiveMessage.setSender_agent_id(Integer.valueOf(decoderMessage.sender_agent_id(recevedBytes))); // decrypt
                receiveMessage.setMessage_content(decoderMessage.message_content(recevedBytes)); // decrypt

                log.info("RECEVIED PACKED Message_Type : {} , Message_Status : {} , Received_Id : {} ," +
                                " Received_Time : {} , From_Phone_Number : {} , To_Phone_Number : {} , Sender_Id : {} , Message_content : {} ",
                        receiveMessage.getMessageType(), receiveMessage.getMessage_status(), receiveMessage.getReceived_id(), receiveMessage.getReceived_time(), receiveMessage.getFrom_phone_number(), receiveMessage.getTo_phone_number(), receiveMessage.getSender_agent_id(), receiveMessage.getMessage_content());
                // receiveMessage 세팅 완료되면 DB에 insert

                // 1. DB 살아있는지 체크 (핑)
                if (receivedMessageService.isAlive()) {
                    log.info("SERVER IS ALIVE");
                } else {
                    socketUtil.socketClose();
                }
                // 2. 살아있으면 insert
                receivedMessageService.insertMessage(receiveMessage);

                // insert한 후 id값이 return 되기 때문에 id 변수 하나 생성
                long id = receiveMessage.getReceived_id();

                // insert 잘되었는지 체크
                if (receivedMessageRepository.findById(id) != null) {
                    log.info("RECEIVE MESSAGE INSERT TO DB SUCCESS");
                }
                // 3. insert 성공 후 SendQueue에 넣고 전달
                // not null이며 자동 증가값을 가진 id값 때문에 0으로 전달했기에 receivedMessage에 따로 뽑은 아이디 값을 set
                receiveMessage.setReceived_id(id);
                socketUtil.getReceiveMessageLinkedBlockingQueue().offer(receiveMessage);
            } else {
                // BAD_ACK 전달받은 값이 null
            }
        } catch (SelectException e) {
            throw new RuntimeException(e);
        }
    }
}
