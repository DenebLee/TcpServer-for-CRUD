package kr.nanoit.tcp;

import kr.nanoit.controller.SocketUtil;
import kr.nanoit.model.message.*;
import kr.nanoit.model.message_Structure.PacketType;
import kr.nanoit.service.ReceivedMessageService;
import kr.nanoit.service.SendToTelecomMessageService;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.TimeUnit;

@Slf4j
public class SendServer implements Runnable {

    private final SocketUtil socketUtil;
    private final SendToTelecomMessageService sendToTelecomMessageService;

    public SendServer(SocketUtil socketUtil, SendToTelecomMessageService sendToTelecomMessageService, ReceivedMessageService receivedMessageService) {
        this.socketUtil = socketUtil;
        this.sendToTelecomMessageService = sendToTelecomMessageService;
    }

    @Override
    public void run() {
        while (true) {
            try {
                SendMessage sendMessage;
                Message message = socketUtil.getReceiveMessageLinkedBlockingQueue().poll(1000, TimeUnit.MICROSECONDS);
                if (message != null) {
                    if (message instanceof LoginMessage) {

                    } else if (message instanceof ReceiveMessage) {
                        ReceiveMessage receiveMessage = (ReceiveMessage) message;
                        sendMessage = makeSendMessage(receiveMessage);
                        System.out.println(sendMessage);

                        // Send DB 에 insert
                        sendToTelecomMessageService.insertMessage(sendMessage);
                        System.out.println("여기서 안되는건가?");

                        // insert 완료 후 sendQueue에 offer
//                        socketUtil.getSendMessageLinkedBlockingQueue().offer(sendMessage);
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
                socketUtil.socketClose();
            }
        }
    }

    private SendMessage makeSendMessage(ReceiveMessage receiveMessage) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.setPacketType(PacketType.SEND_ACKNOWLEDGEMENT);
        sendMessage.setSend_id(0);
        sendMessage.setMessage_status(receiveMessage.getMessage_status());
        sendMessage.setReceived_id(receiveMessage.getReceived_id());
        sendMessage.setMessage_type(receiveMessage.getMessage_type());
        sendMessage.setResult("");

        return sendMessage;
    }

    // Bad Request packet 대응 ack 제작 예정
}
