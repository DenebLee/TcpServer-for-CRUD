package kr.nanoit.service;

import kr.nanoit.model.message.SendMessage;

import java.util.List;

public interface SendToTelecomMessageService {

    List<SendMessage> getSelectedMessage();

    List<SendMessage> getSendMessage();

    void insertMessage(SendMessage sendMessage);

    boolean integrityCheck();

    boolean isAlive();


}
