package kr.nanoit.message.service;

import kr.nanoit.model.message.MessageStatus;
import kr.nanoit.model.message.MessageType;
import kr.nanoit.model.message.ReceiveMessage;
import kr.nanoit.model.message.SendMessage;
import kr.nanoit.exception.message.DeleteException;
import kr.nanoit.exception.message.InsertException;
import kr.nanoit.exception.message.SelectException;
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
    public SendMessageServiceImplTest() throws IOException {
        super("SEND");
        sendToTelecomMessageService = new SendToTelecomMessageServiceImpl(sendToTelecomMessageRepository);
    }

    @BeforeEach
    void setUp() throws  DeleteException, InsertException {
        sendToTelecomMessageRepository.deleteAll();

//        //  외래키가 존재하기 때문에 received DB에 값을 insert
//        List<ReceiveMessage> list = new ArrayList<>();
//        for (int i = 0; i < 20; i++) {
//            ReceiveMessage receiveMessage = new ReceiveMessage(MessageType.SMS, "SEND", MessageStatus.WAIT, 0, new Timestamp(System.currentTimeMillis()), 111, "010444222" + i, "999292112" + i, "안녕" + i);
//            list.add(receiveMessage);
//        }
//        receivedMessageRepository.saveAll(list);
    }

    @Test
    @DisplayName("SEND SERVICE -> getSelectdMessage")
    void should_select_message_when_status_is_select() throws InsertException, SelectException {
        // given
        int count = 20;
        List<SendMessage> list = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            SendMessage sendMessage = new SendMessage(MessageType.SMS, "SEND", MessageStatus.SELECTED, 0, i, "테스트용" + i);
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
        ReceiveMessage receiveMessage = new ReceiveMessage(MessageType.SMS, "SEND", MessageStatus.SELECTED, 1, new Timestamp(System.currentTimeMillis()), 123, "01044445555", "01044446666", "테스트용");
        SendMessage expected = new SendMessage(MessageType.SMS, receiveMessage.getPacket_type(), receiveMessage.getMessage_status(), 0, receiveMessage.getReceived_id(), null);

        // when
        sendToTelecomMessageService.insertMessage(expected);
        SendMessage actual = sendToTelecomMessageRepository.findById(expected.getSend_id());

        // then
        assertThat(actual.getResult()).isEqualTo("Success");
        assertThat(sendToTelecomMessageRepository.count()).isEqualTo(1);
    }
}
