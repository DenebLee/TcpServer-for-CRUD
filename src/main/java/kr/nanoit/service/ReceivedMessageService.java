package kr.nanoit.service;

import kr.nanoit.model.message.ReceiveMessageDto;

import java.util.List;

public interface ReceivedMessageService {

    /*
    select
     */
    List<ReceiveMessageDto> selectAllMessage();


}
