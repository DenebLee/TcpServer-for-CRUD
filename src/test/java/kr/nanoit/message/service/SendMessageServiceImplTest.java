package kr.nanoit.message.service;

import kr.nanoit.core.db.DataBaseQueueList;
import kr.nanoit.model.message.MessageStatus;
import kr.nanoit.model.message.MessageType;
import kr.nanoit.model.message.ReceiveMessage;
import kr.nanoit.model.message.SendMessage;
import kr.nanoit.exception.message.DeleteException;
import kr.nanoit.exception.message.InsertException;
import kr.nanoit.exception.message.SelectException;
import kr.nanoit.repository.SendToTelecomMessageRepository;
import kr.nanoit.service.SendToTelecomMessageService;
import kr.nanoit.service.SendToTelecomMessageServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@Testcontainers
class SendMessageServiceImplTest extends TestServiceSetUp {

    private final SendToTelecomMessageService sendToTelecomMessageService;
    private DataBaseQueueList dataBaseQueueList;

    public SendMessageServiceImplTest() throws IOException {
        super("SEND");
        sendToTelecomMessageService = new SendToTelecomMessageServiceImpl(sendToTelecomMessageRepository);
        dataBaseQueueList = new DataBaseQueueList();
    }

    @BeforeEach
    void setUp() throws IOException, DeleteException, InsertException {
        sendToTelecomMessageRepository = SendToTelecomMessageRepository.createSendMybatis(properties);
        sendToTelecomMessageRepository.deleteAll();


        //  외래키가 존재하기 때문에 received DB에 값을 insert
        List<ReceiveMessage> list = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            ReceiveMessage receiveMessage = new ReceiveMessage(MessageType.SMS, MessageStatus.SELECTED, 0, new Timestamp(System.currentTimeMillis()), i, "010-4444-222" + i, "010-4444-5555", "받은 메시지" + i);
            list.add(receiveMessage);
        }
        receivedMessageRepository.saveAll(list);
    }

    @Test
    @DisplayName("SEND SERVICE -> getSelectdMessage")
    void should_select_message_when_status_is_select() throws InsertException, SelectException {
        // given
        int count = 20;
        List<SendMessage> list = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            SendMessage sendMessage = new SendMessage(MessageType.SMS, MessageStatus.SELECTED, 0, i, "테스트용" + i);
            list.add(sendMessage);
        }
        sendToTelecomMessageRepository.saveAll(list);

        // when
        List<SendMessage> actual = sendToTelecomMessageService.getSelectedMessage();

        // then
        assertThat(actual.size()).isEqualTo((int) sendToTelecomMessageRepository.findAll().stream().filter(sendMessage -> {
            if (sendMessage.getMessage_status().equals(MessageStatus.SEND)) {
                return true;
            } else {
                return false;
            }
        }).count());
    }

    @Test
    @DisplayName("SEND SERVICE -> insertMessage")
    void should_insert_message_when_queue_contains_data() throws SelectException {
        // given
        int count = 20;
        for (int i = 0; i < count; i++) {
            SendMessage dataForQueue = new SendMessage(MessageType.SMS, MessageStatus.SELECTED, 0, i, "성공" + i);
            dataBaseQueueList.getSendMessageLinkedBlockingQueue().offer(dataForQueue);
        }

        // when
        for (int i = 0; i < count; i++) {
            sendToTelecomMessageService.insertMessage(dataBaseQueueList);
        }

        // then
        assertThat(sendToTelecomMessageRepository.findAll().size()).isEqualTo(count);
    }
}
