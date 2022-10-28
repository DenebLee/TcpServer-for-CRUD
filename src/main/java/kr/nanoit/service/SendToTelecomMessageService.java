package kr.nanoit.service;

import kr.nanoit.core.db.DataBaseQueueList;
import kr.nanoit.model.message.SendMessage;
import kr.nanoit.controller.SocketUtil;

import java.util.List;

public interface SendToTelecomMessageService {

    List<SendMessage> getSelectedMessage();

    List<SendMessage> getSendMessage();

    void insertMessage(DataBaseQueueList dataBaseQueueList);

    boolean integrityCheck();


}
