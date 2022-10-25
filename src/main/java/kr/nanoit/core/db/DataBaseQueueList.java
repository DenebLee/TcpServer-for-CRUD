package kr.nanoit.core.db;


import kr.nanoit.model.message.ReceiveMessage;
import lombok.Getter;

import java.util.concurrent.LinkedBlockingQueue;

public class DataBaseQueueList {
    @Getter
    LinkedBlockingQueue<ReceiveMessage> receiveMessageLinkedBlockingQueue;

    public DataBaseQueueList() {
        receiveMessageLinkedBlockingQueue = new LinkedBlockingQueue<>();
    }

}
