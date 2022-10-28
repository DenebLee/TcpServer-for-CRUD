package kr.nanoit.service;

import kr.nanoit.core.db.DataBaseQueueList;
import kr.nanoit.model.message.MessageStatus;
import kr.nanoit.model.message.SendMessage;
import kr.nanoit.controller.SocketUtil;
import kr.nanoit.exception.message.SelectException;
import kr.nanoit.repository.SendToTelecomMessageRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
public class SendToTelecomMessageServiceImpl implements SendToTelecomMessageService {
    private final SendToTelecomMessageRepository send;

    @Override
    public List<SendMessage> getSelectedMessage() {
        List<SendMessage> data = null;
        try {
            if (send.count() > 0) {
                data = send.findAll().stream().filter(sendMessage -> {
                    if (sendMessage.getMessage_status().equals(MessageStatus.SELECTED)) {
                        sendMessage.setMessage_status(MessageStatus.SEND);
                    }
                    return false;
                }).collect(Collectors.toList());
            }
        } catch (SelectException e) {
            throw new RuntimeException(e);
        }
        return data;
    }

    // getSelectedMessage와 기능이 겹치기에 구현 기
    @Override
    public List<SendMessage> getSendMessage() {
        return null;
    }

    @Override
    // Queue에 전달된
    public void insertMessage(DataBaseQueueList dataBaseQueueList) {
        try {
            if (dataBaseQueueList.getSendMessageLinkedBlockingQueue() != null) {
                SendMessage data = dataBaseQueueList.getSendMessageLinkedBlockingQueue().poll(1000, TimeUnit.MICROSECONDS);
                if (data != null) {
                    if (send.save(data) > 0) {
                        log.info("INSERT DB COMPLETE, MESSAGE_TYPE : {}, MESSAGE_STATUS : {}, SEND_ID : {}, RECEIVE_ID : {}, RECEIVED_ID, RESULT : {}", data.getMessage_type(), data.getMessage_status(), data.getSend_id(), data.getReceived_id(), data.getResult());
                    } else {
                        log.warn("INSERT DB FAILED");
                    }

                }
            }
        } catch (InterruptedException | SelectException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean integrityCheck() {
        return false;
    }
}
