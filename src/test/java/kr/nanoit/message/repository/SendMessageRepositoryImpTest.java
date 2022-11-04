package kr.nanoit.message.repository;

import kr.nanoit.model.message.MessageStatus;
import kr.nanoit.model.message.MessageType;
import kr.nanoit.model.message.ReceiveMessage;
import kr.nanoit.model.message.SendMessage;
import kr.nanoit.exception.message.DeleteException;
import kr.nanoit.exception.message.InsertException;
import kr.nanoit.exception.message.SelectException;
import kr.nanoit.exception.message.UpdateException;
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
class SendMessageRepositoryImpTest extends TestRepositorySetUp {

    private ReceiveMessage receiveMessage;
    private long id;

    public SendMessageRepositoryImpTest() throws IOException {
        super("SEND");
    }

    @BeforeEach
    void setUp() throws DeleteException, InsertException {
        sendToTelecomMessageRepository.deleteAll();
        receiveMessage = new ReceiveMessage(MessageType.SMS, "SEND", MessageStatus.SELECTED, 0, new Timestamp(System.currentTimeMillis()), 3, "010-4040-4141", "010-4444-5555", "테스트");
        receivedMessageRepository.save(receiveMessage);
        id = receiveMessage.getReceived_id();
    }

    @Test
    @DisplayName("SEND -> SAVE")
    void should_send_save() throws SelectException, InsertException {
        // given
        SendMessage sendMessage = new SendMessage(MessageType.SMS, "SEND_ACK", MessageStatus.SELECTED, 0, id, "Send Test Value");

        // when
        int actual = sendToTelecomMessageRepository.save(sendMessage);

        // then
        assertThat(actual).isEqualTo(1);
    }

    @Test
    @DisplayName("SEND -> COUNT")
    void should_send_count() throws InsertException, SelectException {
        // given
        receivedMessageRepository.save(receiveMessage);
        SendMessage expected = new SendMessage(MessageType.SMS, "SEND_ACK", MessageStatus.SELECTED, 0, id, "Data for Count");
        sendToTelecomMessageRepository.save(expected);

        // when
        int actual = sendToTelecomMessageRepository.count();

        // then
        assertThat(actual).isEqualTo(1);
    }

    @Test
    @DisplayName("SEND -> FIND BY ID")
    void should_return_when_given_id() throws SelectException {
        // given
        SendMessage expected = new SendMessage(MessageType.SMS, "SEND_ACK", 1, 0, id, "Data for findById");
        sendToTelecomMessageRepository.save(expected);
        long id = expected.getSend_id();

        // when
        SendMessage actual = sendToTelecomMessageRepository.findById(id);

        // then
        assertThat(actual.getPacketType()).isEqualTo(expected.getPacketType());
        assertThat(actual.getStatus()).isEqualTo(expected.getStatus());
        assertThat(actual.getReceived_id()).isEqualTo(expected.getReceived_id());
        assertThat(actual.getResult()).isEqualTo(expected.getResult());
    }

    @Test
    @DisplayName("SEND -> DELETE BY ID")
    void should_send_delete_by_id() throws SelectException {
        // given
        SendMessage expected = new SendMessage(MessageType.SMS, "SEND_ACK", MessageStatus.SELECTED, 0, id, "Data for Delete");
        sendToTelecomMessageRepository.save(expected);
        long send_id = expected.getSend_id();

        // when
        boolean actual = sendToTelecomMessageRepository.deleteById(send_id);

        // then
        assertThat(actual).isTrue();
    }

    @Test
    @DisplayName("SEND -> SAVE ALL")
    void should_send_save_all() throws InsertException, SelectException {
        // given
        List<SendMessage> expected = new ArrayList<>();
        int count = 100;
        for (int i = 0; i < count; i++) {
            expected.add(new SendMessage(MessageType.SMS, "SEND_ACK", MessageStatus.SELECTED, 0, id, "테스트용" + i));
        }
        // when
        int actual = sendToTelecomMessageRepository.saveAll(expected);

        // then
        assertThat(actual).isEqualTo(count);
        assertThat(sendToTelecomMessageRepository.count()).isEqualTo(count);
    }

    @Test
    @DisplayName("SEND -> DELETE ALL")
    void should_delete_all() throws InsertException, DeleteException {
        // given
        List<SendMessage> expected = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            expected.add(new SendMessage(MessageType.SMS, "SEND_ACK", MessageStatus.SELECTED, 0, id, "테스트용" + i));
        }
        sendToTelecomMessageRepository.saveAll(expected);

        // when
        boolean actual = sendToTelecomMessageRepository.deleteAll();

        // then
        assertThat(actual).isTrue();
    }

    @Test
    @DisplayName("SEND -> UPDATE")
    void should_send_update() throws SelectException, UpdateException {
        // given
        SendMessage originalData = new SendMessage(MessageType.SMS, "SEND_ACK", MessageStatus.WAIT, 0, id, "안녕하세요");
        sendToTelecomMessageRepository.save(originalData);
        long send_id = originalData.getSend_id();
        SendMessage expected = new SendMessage(MessageType.SMS, "SEND_ACK", MessageStatus.SELECTED, send_id, id, "수정된 값");

        // when
        int actual = sendToTelecomMessageRepository.update(expected);

        // then
        assertThat(actual).isEqualTo(1);
        assertThat(sendToTelecomMessageRepository.findById(send_id)).isNotEqualTo(originalData);
    }
}


