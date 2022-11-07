package kr.nanoit.tcp;

import kr.nanoit.core.db.DataBaseSessionManager;
import kr.nanoit.model.message_Structure.PacketType;
import kr.nanoit.repository.ReceivedMessageRepository;
import kr.nanoit.service.ReceivedMessageService;
import kr.nanoit.service.ReceivedMessageServiceImpl;
import kr.nanoit.util.Crypt;
import kr.nanoit.controller.SocketUtil;
import kr.nanoit.exception.message.InsertException;
import kr.nanoit.exception.message.SelectException;
import kr.nanoit.model.decoder.DecoderLogin;
import kr.nanoit.model.decoder.DecoderMessage;
import kr.nanoit.model.message.LoginMessage;
import kr.nanoit.model.message.MessageType;
import kr.nanoit.model.message.ReceiveMessage;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.util.List;
import java.util.Properties;


@Slf4j
public class ReceiveServer implements Runnable {


    private SocketUtil socketUtil;
    private final DecoderLogin decoderLogin;
    private ReceivedMessageService receivedMessageService;
    private DecoderMessage decoderMessage;


    public ReceiveServer(SocketUtil socketUtil, ReceivedMessageService receivedMessageService) {
        this.receivedMessageService = receivedMessageService;
        this.socketUtil = socketUtil;
        decoderLogin = new DecoderLogin();
        decoderMessage = new DecoderMessage();
    }

    @Override
    public void run() {
        if (socketUtil.getSocket().isConnected()) {
            log.info("RECEIVE SERVER Client Connected !!!!! {}", socketUtil.getSocket().getInetAddress().getAddress());
        }
        try {
            while (true) {
                byte[] receiveByte = socketUtil.receiveByte();
                if (receiveByte != null) {
                    switch (socketUtil.getPacketType(receiveByte)) {
                        case SEND:
                            decode_send(receiveByte);
                            break;
                        case LOGIN:
                            decode_login(receiveByte);
                            break;
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            socketUtil.socketClose();
        }
    }

    public void decode_login(byte[] receiveBytes) {
        LoginMessage loginMessage = new LoginMessage();

        loginMessage.setPacketType(PacketType.LOGIN);
        loginMessage.setId(decoderLogin.id(receiveBytes));
        loginMessage.setPassword(decoderLogin.id(receiveBytes));
        loginMessage.setMessageType(MessageType.LOGIN);

        // 접속하고자 하는 유저의 Login_Packet 검증
    }

    public void decode_send(byte[] recevedBytes) throws InsertException {
        try {
            if (recevedBytes != null) {
                ReceiveMessage receiveMessage = new ReceiveMessage();

                receiveMessage.setMessage_type(decoderMessage.messageType(recevedBytes));

                receiveMessage.setReceived_id(0);
                receiveMessage.setFrom_phone_number(decoderMessage.from_phone_number(recevedBytes));
                receiveMessage.setTo_phone_number(decoderMessage.to_phone_number(recevedBytes));
                receiveMessage.setSender_agent_id(decoderMessage.sender_agent_id(recevedBytes));
                receiveMessage.setMessage_content(decoderMessage.message_content(recevedBytes));

                log.info("RECEVIED PACKED Message_Type : {} , Message_Status : {} , Received_Id : {} ," +
                                "  From_Phone_Number : {} , To_Phone_Number : {} , Sender_Id : {} , Message_content : {} ",
                        receiveMessage.getMessage_type(), receiveMessage.getMessage_status(), receiveMessage.getReceived_id(), receiveMessage.getFrom_phone_number(), receiveMessage.getTo_phone_number(), receiveMessage.getSender_agent_id(), receiveMessage.getMessage_content());

                if (receivedMessageService.isAlive() == true) {
                    log.info("SERVER IS ALIVE");
                } else if (receivedMessageService.isAlive() == false) {
                    socketUtil.socketClose();
                }
                int result = receivedMessageService.insertMessage(receiveMessage);
                if (result > 0) {
                    List<ReceiveMessage> listData = receivedMessageService.selectAllMessage();
                    for (ReceiveMessage receiveMessage1 : listData) {
                        socketUtil.getReceiveMessageLinkedBlockingQueue().offer(receiveMessage1);
                    }
                }
            } else {
                // BAD_ACK 전달받은 값이 null
            }
        } catch (SelectException e) {
            throw new RuntimeException(e);
        }
    }
}
