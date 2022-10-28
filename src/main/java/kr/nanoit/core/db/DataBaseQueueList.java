package kr.nanoit.core.db;

import kr.nanoit.model.message.ReceiveMessage;
import kr.nanoit.model.message.SendMessage;
import lombok.Getter;

import java.util.concurrent.LinkedBlockingQueue;

public class DataBaseQueueList {

    @Getter
    LinkedBlockingQueue<ReceiveMessage> receiveMessageLinkedBlockingQueue;

    @Getter
    LinkedBlockingQueue<SendMessage> sendMessageLinkedBlockingQueue;

    public DataBaseQueueList() {
        receiveMessageLinkedBlockingQueue = new LinkedBlockingQueue<>();
        sendMessageLinkedBlockingQueue = new LinkedBlockingQueue<>();
    }
}
