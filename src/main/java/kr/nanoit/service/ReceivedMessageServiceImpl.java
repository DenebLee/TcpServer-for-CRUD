package kr.nanoit.service;


import kr.nanoit.model.message.MessageStatus;
import kr.nanoit.exception.message.InsertException;
import kr.nanoit.exception.message.SelectException;
import kr.nanoit.model.message.ReceiveMessage;
import kr.nanoit.repository.ReceivedMessageRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.sql.Timestamp;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
public class ReceivedMessageServiceImpl implements ReceivedMessageService {

    private final ReceivedMessageRepository receive;

    @Override
    public List<ReceiveMessage> selectMessageMatchedCondition(String condition) {
        try {
            return receive.findAll().stream().filter(messageDto -> {
                if (messageDto.getMessage_content().contains(condition)) {
                    return true;
                } else {
                    return false;
                }
            }).collect(Collectors.toList());
        } catch (SelectException e) {
            throw new RuntimeException("not found");
        }
    }

    @Override
    public List<ReceiveMessage> selectAllMessage() {
        List<ReceiveMessage> data = null;
        try {
            data = receive.findAll().stream().filter(receiveMessage -> {
                if (receiveMessage.getMessage_status().equals(MessageStatus.WAIT)) {
                    receiveMessage.setMessage_status(MessageStatus.SELECTED);
                    return true;
                } else {
                    return false;
                }
            }).collect(Collectors.toList());
            for (ReceiveMessage receiveMessage : data) {
                if (receiveMessage.getMessage_status().equals(MessageStatus.SELECTED)) {
                    receive.update(receiveMessage);
                } else {
                    receiveMessage.setMessage_status(MessageStatus.SELECTED);
                    receive.update(receiveMessage);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return data;
    }

    @Override
    public Integer insertMessage(ReceiveMessage receiveMessage) {
        Timestamp currentTime = new Timestamp(System.currentTimeMillis());
        try {
            if (receiveMessage != null) {
                receiveMessage.setMessage_status(MessageStatus.WAIT);
                receiveMessage.setReceived_time(currentTime);
                int result = receive.save(receiveMessage);
                if (result > 0) {
                    log.info("INSERT DB COMPLETE MESSAGE_TYPE : {} , MESSAGE_STATUS : {} , RECEIVED_ID : {} , RECEIVED_TIME : {} , SENDER_AGENT_ID : {} , TO_PHONE_NUMBER : {}, FROM_PHONE_NUMBER : {}, MESSAGE_CONTENT : {}", receiveMessage.getMessageType(), receiveMessage.getMessage_status(), receiveMessage.getReceived_id(), receiveMessage.getReceived_time(), receiveMessage.getSender_agent_id(), receiveMessage.getTo_phone_number(), receiveMessage.getFrom_phone_number(), receiveMessage.getMessage_content());
                    return result;
                } else {
                    log.warn("INSERT DB FAILED");
                    throw new InsertException("Insert failed");
                }
            }
        } catch (InsertException e) {
            throw new RuntimeException(e);
        }
        return 0;
    }


    @Override
    public boolean isNotExist() {
        return false;
    }

    @Override
    public boolean validation(ReceiveMessage receiveMessage) {
        try {
            ReceiveMessage validationdData = receive.findById(receiveMessage.getReceived_id());
            if (validationdData != null) {
                if (validationdData.getMessage_status().equals(receiveMessage.getMessage_status()) && validationdData.getMessageType().equals(receiveMessage.getMessageType())
                        && validationdData.getReceived_time().equals(receiveMessage.getReceived_time()) && validationdData.getFrom_phone_number().equals(receiveMessage.getFrom_phone_number())
                        && validationdData.getTo_phone_number().equals(receiveMessage.getTo_phone_number()) && validationdData.getMessage_content().equals(receiveMessage.getMessage_content())) {
                    System.out.println("여기까지 확인 필요");
                    return true;
                }
            }
        } catch (SelectException e) {
            throw new RuntimeException(e);
        }
        return false;
    }

    @Override
    public boolean isAlive() {
        try {
            if (receive.alive()) {
                log.info("THE SERVER IS ALIVE");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }


}
