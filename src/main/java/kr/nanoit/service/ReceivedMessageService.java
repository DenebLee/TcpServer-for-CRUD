package kr.nanoit.service;


import kr.nanoit.exception.message.SelectException;
import kr.nanoit.model.message.ReceiveMessage;
import kr.nanoit.exception.message.InsertException;

import java.util.List;

public interface ReceivedMessageService {
    List<ReceiveMessage> selectMessageMatchedCondition(String condition);

    List<ReceiveMessage> selectAllMessage();

    Integer insertMessage(ReceiveMessage receiveMessage) throws InsertException;

    boolean isNotExist();

    boolean validation(ReceiveMessage receiveMessage);

    boolean isAlive() throws SelectException;

}
