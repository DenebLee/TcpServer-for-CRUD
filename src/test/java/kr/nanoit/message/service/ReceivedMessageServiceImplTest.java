package kr.nanoit.message.service;


import kr.nanoit.model.message.MessageStatus;
import kr.nanoit.model.message.MessageType;
import kr.nanoit.model.message.ReceiveMessage;
import kr.nanoit.exception.message.DeleteException;
import kr.nanoit.exception.message.InsertException;
import kr.nanoit.exception.message.SelectException;
import kr.nanoit.service.ReceivedMessageService;
import kr.nanoit.service.ReceivedMessageServiceImpl;
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
class ReceivedMessageServiceImplTest extends TestServiceSetUp {
    private final ReceivedMessageService receivedMessageService;

    public ReceivedMessageServiceImplTest() throws IOException {
        super("RECEIVE");
        receivedMessageService = new ReceivedMessageServiceImpl(receivedMessageRepository);
    }

    @BeforeEach
    void setUp() throws DeleteException {
        receivedMessageRepository.deleteAll();
    }

    @Test
    @DisplayName("RECEIVE SERVICE -> SELECT ALL")
    void should_select_message_when_status_is_wait() throws InsertException {
        // given
        // 테스트 칼럼 10개 세팅
        List<ReceiveMessage> dataSetList = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            ReceiveMessage dataSet = new ReceiveMessage(MessageType.SMS, MessageStatus.WAIT, 0, new Timestamp(System.currentTimeMillis()), 1 + i, "010-4444-1111", "010-4444-5353", "테스트용" + i);
            dataSetList.add(dataSet);
        }
        receivedMessageRepository.saveAll(dataSetList);
        // when
        List<ReceiveMessage> actual = receivedMessageService.selectAllMessage();

        // then
    }

    @Test
    @DisplayName("RECEIVED SERVICE -> SELECT MESSAGE MATCHED CONDITION ")
    void should() throws InsertException, SelectException {
        List<ReceiveMessage> test = new ArrayList<>();
        int count = 424;
        int count2 = 414;

        for (int i = 0; i < count; i++) {
            test.add(new ReceiveMessage(MessageType.SMS, MessageStatus.WAIT, 0, new Timestamp(System.currentTimeMillis()), 1 + i, "010-4444-1111", "010-4444-5353", "안녕하세요" + i));
        }
        for (int i = 0; i < count2; i++) {
            test.add(new ReceiveMessage(MessageType.SMS, MessageStatus.WAIT, 0, new Timestamp(System.currentTimeMillis()), 1 + i, "010-4444-1111", "010-4444-5353", "안뇽하세요" + i));
        }

        int total_count = receivedMessageRepository.saveAll(test);

        List<ReceiveMessage> test2 = receivedMessageService.selectMessageMatchedCondition("안녕하세요");

        assertThat(test2.size()).isEqualTo(count);
        assertThat(receivedMessageRepository.count()).isEqualTo(total_count);
    }

    @Test
    @DisplayName("RECEIVED SERVICE -> INSERT MESSAGE TO DB")
    void should_insert_when_received_quere_have_a_data() throws InsertException, SelectException {
        // given
        ReceiveMessage expected = new ReceiveMessage(MessageType.SMS, null, 0, null, 1, "010-4444-1111", "010-4444-5353", "보냅니다 메시지 받으세영");

        // when
        receivedMessageService.insertMessage(expected);

        // then
        ReceiveMessage result = receivedMessageRepository.findById(849);
        // 한개만 세팅했기에 id는 1
        assertThat(receivedMessageRepository.findById(849)).isNotNull();
        assertThat(result.getStatus()).isEqualTo(MessageStatus.WAIT);
        assertThat(receivedMessageRepository.count()).isEqualTo(1);
    }
}
