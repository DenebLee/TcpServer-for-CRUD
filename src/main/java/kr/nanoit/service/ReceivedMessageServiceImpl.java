package kr.nanoit.service;

import kr.nanoit.core.db.DataBaseQueueList;
import kr.nanoit.model.message.MessageStatus;
import kr.nanoit.old.exception.message.InsertException;
import kr.nanoit.old.exception.message.SelectException;
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

    private final ReceivedMessageRepository receivedMessageRepository;

    @Override
    public List<ReceiveMessage> selectMessageMatchedCondition(String condition) {
        try {
            return receivedMessageRepository.findAll().stream().filter(messageDto -> {
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
        List<ReceiveMessage> selectDataList = null;
        try {
            return receivedMessageRepository.findAll().stream().filter(receiveMessage -> {
                if (receiveMessage.getMessage_status().equals(MessageStatus.WAIT)) {
                    receiveMessage.setMessage_status(MessageStatus.SELECTED);
                    return selectDataList.add(receiveMessage);
                }
                return false;
            }).collect(Collectors.toList());
        } catch (SelectException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public ReceiveMessage insertMessage(DataBaseQueueList list) throws InsertException {
        Timestamp currentTime = new Timestamp(System.currentTimeMillis());
        if (list != null) {
            try {
                ReceiveMessage data = list.getReceiveMessageLinkedBlockingQueue().poll(1000, TimeUnit.MICROSECONDS);
                if (data != null) {
                    data.setMessage_status(MessageStatus.WAIT);
                    data.setReceived_time(currentTime);
                    if (receivedMessageRepository.save(data) > 0) {
                        log.info("INSERT DB COMPLETE");
                    } else {
                        log.warn("INSERT DB FAILED");
                    }
                }
            } catch (InsertException e) {
                // 오류패킷 전달
                e.getReason();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        throw new InsertException("Serve Error");
    }


    @Override
    public boolean isNotExist() {
        return false;
    }


}
