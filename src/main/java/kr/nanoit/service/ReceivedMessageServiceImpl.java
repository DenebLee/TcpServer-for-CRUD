package kr.nanoit.service;

import kr.nanoit.old.exception.message.SelectException;
import kr.nanoit.model.message.ReceiveMessageDto;
import kr.nanoit.repository.ReceivedMessageRepository;
import kr.nanoit.repository.SendToTelecomMessageRepository;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
public class ReceivedMessageServiceImpl implements ReceivedMessageService {

    private final ReceivedMessageRepository receivedMessageRepository;
    private final SendToTelecomMessageRepository sendToTelecomMessageRepository;

    @Override
    public List<ReceiveMessageDto> selectAllMessage() {
        try {
            return receivedMessageRepository.findAll().stream().filter(messageDto -> {
                if (messageDto.getMessage_content().contains("IT")) {
                    return true;
                } else {
                    return false;
                }
            }).collect(Collectors.toList());
        } catch (SelectException e) {
            throw new RuntimeException("not found");
        }
    }


}
