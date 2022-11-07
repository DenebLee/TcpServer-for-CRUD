package kr.nanoit.service;

import kr.nanoit.model.message.SendMessage;

import java.util.List;

public interface SendToTelecomMessageService {

    List<SendMessage> getSelectedMessage();

    List<SendMessage> getSendMessage();

    Integer insertMessage(SendMessage sendMessage);

    boolean integrityCheck();

    boolean isAlive();


}
