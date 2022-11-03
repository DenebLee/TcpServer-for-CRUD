package kr.nanoit.service;


import kr.nanoit.exception.message.SelectException;
import kr.nanoit.model.message.ReceiveMessage;
import kr.nanoit.exception.message.InsertException;

import java.util.List;

public interface ReceivedMessageService {
    List<ReceiveMessage> selectMessageMatchedCondition(String condition);

    List<ReceiveMessage> selectAllMessage();

    long insertMessage(ReceiveMessage receiveMessage) throws InsertException;

    boolean referenceReceiveDB(ReceiveMessage receiveMessage);

    boolean isNotExist();

    boolean isAlive() throws SelectException;

}
