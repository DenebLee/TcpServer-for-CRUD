package kr.nanoit.http;

import kr.nanoit.model.message.MessageDto;
import kr.nanoit.service.ReceivedMessageService;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public class GetMessageHandler {

    private final ReceivedMessageService receivedMessageService;

    public List<MessageDto> responseHandle() {
        return receivedMessageService.selectAllMessage();
    }
}
