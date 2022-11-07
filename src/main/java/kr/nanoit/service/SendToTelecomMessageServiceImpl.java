package kr.nanoit.service;

import kr.nanoit.exception.message.UpdateException;
import kr.nanoit.model.message.MessageStatus;
import kr.nanoit.model.message.SendMessage;
import kr.nanoit.exception.message.SelectException;
import kr.nanoit.repository.SendToTelecomMessageRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
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


    @Override
    public List<SendMessage> getSendMessage() {
        return null;
    }

    @Override
    // Queue에 전달된
    public Integer insertMessage(SendMessage sendMessage) {
        try {
            if (sendMessage != null) {
                int count = send.save(sendMessage);
                if (count > 0) {
                    sendMessage.setResult("Success");
                    send.update(sendMessage);
                    log.info("INSERT DB COMPLETE, MESSAGE_TYPE : {}, MESSAGE_STATUS : {}, SEND_ID : {}, RECEIVE_ID : {}, RECEIVED_ID, RESULT : {}", sendMessage.getMessage_type(), sendMessage.getMessage_status(), sendMessage.getSend_id(), sendMessage.getReceived_id(), sendMessage.getResult());
                    return count;
                } else {
                    log.warn("INSERT DB FAILED");
                }
            }
        } catch (SelectException | UpdateException e) {
            throw new RuntimeException(e);
        }
        return 0;
    }

    @Override
    public boolean integrityCheck() {
        return false;
    }

    @Override
    public boolean isAlive() {
        return false;
    }
}
