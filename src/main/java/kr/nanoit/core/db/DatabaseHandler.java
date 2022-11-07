package kr.nanoit.core.db;

import kr.nanoit.repository.ReceivedMessageRepository;
import kr.nanoit.repository.SendToTelecomMessageRepository;
import kr.nanoit.service.ReceivedMessageService;
import kr.nanoit.service.ReceivedMessageServiceImpl;
import kr.nanoit.service.SendToTelecomMessageService;
import kr.nanoit.service.SendToTelecomMessageServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.io.Resources;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

@Slf4j
public class DatabaseHandler {
    private ReceivedMessageRepository receivedMessageRepository;
    private ReceivedMessageService receivedMessageService;
    private SendToTelecomMessageRepository sendToTelecomMessageRepository;
    private SendToTelecomMessageService sendToTelecomMessageService;
    private Properties properties;

    public DatabaseHandler(Properties properties) {
        this.properties = properties;
    }

    public ReceivedMessageService getReceivedMessageService() throws IOException {
        receivedMessageRepository = ReceivedMessageRepository.createReceiveRepository(properties);
        receivedMessageService = new ReceivedMessageServiceImpl(receivedMessageRepository);
        return receivedMessageService;
    }

    public SendToTelecomMessageService getSendMessageService() throws IOException {
        sendToTelecomMessageRepository = SendToTelecomMessageRepository.createSendMybatis(properties);
        sendToTelecomMessageService = new SendToTelecomMessageServiceImpl(sendToTelecomMessageRepository);
        return sendToTelecomMessageService;
    }
}
