package kr.nanoit.service;

import kr.nanoit.model.message.MessageDto;

import java.util.List;

public interface ReceivedMessageService {

    /*
    select
     */
    List<MessageDto> selectAllMessage();

//    MessageDto selectByTo_Phone_Num();

//
//    MessageDto selectByFrom_Phone_Num();
//
//    MessageDto selectByMsg_Type();
//
//    /*
//    delete
//     */
//    boolean deleteByFrom_Phone_Num();
//
//    boolean deleteByMsg_Id();
//
//    boolean deleteByTo_Phone_Num();
//
//    boolean deleteByMesg_Type();
//
//
//    /*
//    update
//     */
//    MessageDto updateByMsg_Id();
//
//    /*
//    insert -> agent가 보냈다는 가정하에 insert시 messagePacket전송
//     */
//    MessageDto insertByMesg_Id();
}
