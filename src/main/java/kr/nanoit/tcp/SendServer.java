package kr.nanoit.tcp;

import kr.nanoit.controller.SocketUtil;
import kr.nanoit.model.message.LoginMessage;
import kr.nanoit.model.message.Message;
import kr.nanoit.model.message.ReceiveMessage;
import kr.nanoit.model.message.SendMessage;
import kr.nanoit.model.message_Structure.PacketType;
import kr.nanoit.repository.ReceivedMessageRepository;
import kr.nanoit.repository.SendToTelecomMessageRepository;
import kr.nanoit.service.ReceivedMessageService;
import kr.nanoit.service.ReceivedMessageServiceImpl;
import kr.nanoit.service.SendToTelecomMessageService;
import kr.nanoit.service.SendToTelecomMessageServiceImpl;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

@Slf4j
public class SendServer implements Runnable {

    private final SocketUtil socketUtil;
    private final ReceivedMessageService receivedMessageService;
    private final ReceivedMessageRepository receivedMessageRepository;
    private final SendToTelecomMessageService sendToTelecomMessageService;
    private final SendToTelecomMessageRepository sendToTelecomMessageRepository;


    public SendServer(SocketUtil socketUtil, Properties prop) throws IOException {
        this.socketUtil = socketUtil;
        receivedMessageRepository = ReceivedMessageRepository.createReceiveRepository(prop);
        receivedMessageService = new ReceivedMessageServiceImpl(receivedMessageRepository);

        sendToTelecomMessageRepository = SendToTelecomMessageRepository.createSendMybatis(prop);
        sendToTelecomMessageService = new SendToTelecomMessageServiceImpl(sendToTelecomMessageRepository);
    }

    @Override
    public void run() {
        while (true) {
            try {
                Message message = socketUtil.getSendMessageLinkedBlockingQueue().poll(1000, TimeUnit.MICROSECONDS);
                if (message != null) {
                    if (message instanceof LoginMessage) {

                    } else if (message instanceof ReceiveMessage) {
                        ReceiveMessage receiveMessage = (ReceiveMessage) message;
                        if (receiveMessage.getReceived_id() != 0) {
                            if (receivedMessageService.referenceReceiveDB(receiveMessage)) {
                                log.info("RECEIVE DB MESSAGE REFERENCE SUCCESS");
                            }
                            SendMessage sendMessage = makeSendMessage(receiveMessage);
                            // Send DB 에 insert
                            sendToTelecomMessageService.insertMessage(sendMessage);

                            // insert 완료 후 sendQueue에 offer
                            socketUtil.getSendMessageLinkedBlockingQueue().offer(sendMessage);
                        }
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private SendMessage makeSendMessage(ReceiveMessage receiveMessage) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.setSend_id(0);
        sendMessage.setMessage_status(receiveMessage.getMessage_status());
        sendMessage.setReceived_id(receiveMessage.getReceived_id());
        sendMessage.setMessage_type(receiveMessage.getMessage_type());

        return sendMessage;
    }

    // Bad Request packet 대응 ack 제작 예정
}
