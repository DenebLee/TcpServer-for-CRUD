package kr.nanoit.service;

import kr.nanoit.core.db.DataBaseQueueList;
import kr.nanoit.model.message.MessageStatus;
import kr.nanoit.controller.SocketUtil;
import kr.nanoit.exception.message.InsertException;
import kr.nanoit.exception.message.SelectException;
import kr.nanoit.model.message.ReceiveMessage;
import kr.nanoit.repository.ReceivedMessageRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.sql.Timestamp;
import java.util.List;
import java.util.concurrent.TimeUnit;
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
        } catch (Exception e) {
            e.printStackTrace();
        }
        return data;
    }

    @Override
    public Integer insertMessage(DataBaseQueueList dataBaseQueueList) throws InsertException {
        Timestamp currentTime = new Timestamp(System.currentTimeMillis());
        try {
            if (dataBaseQueueList.getReceiveMessageLinkedBlockingQueue() != null) {
                ReceiveMessage data = dataBaseQueueList.getReceiveMessageLinkedBlockingQueue().poll(1000, TimeUnit.MICROSECONDS);
                // 일정 시간 기다렸다가 대기후 객체 추가
                if (data != null) {
                    data.setMessage_status(MessageStatus.WAIT);
                    data.setReceived_time(currentTime);
                    int a = receive.save(data);
                    if (a > 0) {
                        log.info("INSERT DB COMPLETE MESSAGE_TYPE : {} , MESSAGE_STATUS : {} , RECEIVED_ID : {} , RECEIVED_TIME : {} , SENDER_AGENT_ID : {} , TO_PHONE_NUMBER : {}, FROM_PHONE_NUMBER : {}, MESSAGE_CONTENT : {}", data.getMessageType(), data.getMessage_status(), data.getReceived_id(), data.getReceived_time(), data.getSender_agent_id(), data.getTo_phone_number(), data.getFrom_phone_number(), data.getMessage_content());
                        return a;
                    } else {
                        log.warn("INSERT DB FAILED");
                        throw new InsertException("Insert failed");
                    }
                }
            }
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        return null;
    }


    @Override
    public boolean isNotExist() {
        return false;
    }


}
