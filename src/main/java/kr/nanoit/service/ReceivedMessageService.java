package kr.nanoit.service;

import kr.nanoit.core.db.DataBaseQueueList;
import kr.nanoit.model.message.ReceiveMessage;
import kr.nanoit.controller.SocketUtil;
import kr.nanoit.exception.message.InsertException;

import java.util.List;

public interface ReceivedMessageService {
    List<ReceiveMessage> selectMessageMatchedCondition(String condition);

    List<ReceiveMessage> selectAllMessage();

    Integer insertMessage(DataBaseQueueList dataBaseQueueList) throws InsertException;

    boolean isNotExist();

}
