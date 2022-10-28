package kr.nanoit.tcp;

import kr.nanoit.controller.SocketUtil;
import kr.nanoit.model.message.LoginMessage;
import kr.nanoit.model.message.MessageStatus;
import kr.nanoit.model.message.MessageType;
import kr.nanoit.model.message.ReceiveMessage;
import kr.nanoit.repository.ReceivedMessageRepository;
import kr.nanoit.service.ReceivedMessageService;
import kr.nanoit.service.ReceivedMessageServiceImpl;
import lombok.extern.slf4j.Slf4j;

import java.sql.Timestamp;

@Slf4j
public class ReceiveServer implements Runnable {
    private ReceivedMessageService receivedMessageService;
    private SocketUtil socketUtil;


    public ReceiveServer(ReceivedMessageRepository receivedMessageRepository, SocketUtil socketUtil) {
        this.receivedMessageService = new ReceivedMessageServiceImpl(receivedMessageRepository);
        this.socketUtil = socketUtil;
    }

    @Override
    public void run() {
        try {
            while (true) {
                byte[] receiveByte = socketUtil.receiveByte();
                if (receiveByte != null) {
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
            throw new RuntimeException(e);
        }
    }

    public void decode_login(byte[] receiveBytes) {
        LoginMessage loginMessage = new LoginMessage();
    }

    public void decode_send(byte[] recevedBytes) {
        ReceiveMessage receiveMessage = new ReceiveMessage();
        receiveMessage.setMessage_type(MessageType.SMS);
        receiveMessage.setMessage_status(MessageStatus.WAIT);
        receiveMessage.setReceived_id(0);
        receiveMessage.setReceived_time(new Timestamp(System.currentTimeMillis()));
        receiveMessage.setFrom_phone_number("안에 패킷벗겨낸 값을 string으로 변환하여 넣기");
        receiveMessage.setTo_phone_number("안에 패킷벗겨낸 값을 string으로 변환하여 넣기");
        receiveMessage.setSender_agent_id(0);
        receiveMessage.setMessage_content("패킷 벗겨낸 값");

        log.info("RECEVIED PACKED Message_Type : {} , Message_Status : {} , Received_Id : {} ," +
                        " Received_Time : {} , From_Phone_Number : {} , To_Phone_Number : {} , Sender_Id : {} , Message_content : {} ",
                receiveMessage.getMessageType(), receiveMessage.getMessage_status(), receiveMessage.getReceived_id(), receiveMessage.getReceived_time(), receiveMessage.getFrom_phone_number(), receiveMessage.getTo_phone_number(), receiveMessage.getSender_agent_id(), receiveMessage.getMessage_content());
        // Dto로 변환이 된 Received_message는 Queue에 담아 전달
        socketUtil.getReceiveMessageLinkedBlockingQueue().offer(receiveMessage);
    }
}
