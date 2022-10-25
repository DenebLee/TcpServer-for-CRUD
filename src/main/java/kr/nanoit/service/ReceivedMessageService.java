package kr.nanoit.service;

import kr.nanoit.core.db.DataBaseQueueList;
import kr.nanoit.model.message.Message;
import kr.nanoit.model.message.ReceiveMessage;
import kr.nanoit.old.exception.message.InsertException;

import java.util.List;
import java.util.concurrent.LinkedBlockingQueue;

public interface ReceivedMessageService {
    List<ReceiveMessage> selectMessageMatchedCondition(String condition);

    List<ReceiveMessage> selectAllMessage();

    ReceiveMessage insertMessage(DataBaseQueueList list) throws InsertException;

    boolean isNotExist();
}
