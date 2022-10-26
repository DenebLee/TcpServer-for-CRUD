package kr.nanoit.service;

import kr.nanoit.core.db.DataBaseQueueList;
import kr.nanoit.model.message.ReceiveMessage;
import kr.nanoit.old.exception.message.InsertException;

import java.util.List;

public interface ReceivedMessageService {
    List<ReceiveMessage> selectMessageMatchedCondition(String condition);

    List<ReceiveMessage> selectAllMessage();

    void insertMessage(DataBaseQueueList list) throws InsertException;

    boolean isNotExist();
}
