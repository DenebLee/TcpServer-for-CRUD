package message;

import kr.nanoit.model.message.MessageType;
import kr.nanoit.old.exception.message.DeleteException;
import kr.nanoit.old.exception.message.SelectException;
import kr.nanoit.repository.ReceivedMessageRepository;
import kr.nanoit.old.exception.message.InsertException;
import kr.nanoit.model.message.MessageDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import static org.assertj.core.api.Assertions.assertThat;

@Testcontainers
class ReceivedMessageRepositoryImplTest {
    private ReceivedMessageRepository receivedMessageRepository;
    Timestamp receiveTime;
    @Container
    public PostgreSQLContainer postgreSQLContainer = new PostgreSQLContainer("postgres:14.5-alpine");

    @BeforeEach
    void setUp() throws IOException {
        Properties properties = new Properties();
        properties.setProperty("driver", postgreSQLContainer.getDriverClassName());
        properties.setProperty("url", postgreSQLContainer.getJdbcUrl());
        properties.setProperty("username", postgreSQLContainer.getUsername());
        properties.setProperty("password", postgreSQLContainer.getPassword());
        properties.setProperty("mapper", "MSG_POSTGRESQL.xml");

        receivedMessageRepository = ReceivedMessageRepository.createMybatis(properties);

        receiveTime = new Timestamp(System.currentTimeMillis());

    }

    @Test
    @DisplayName("SELECT BY ID")
    void should_select_by_id() throws InsertException, SelectException {
        // given
        MessageDto expected = new MessageDto(0, MessageType.SMS, receiveTime, 4, "010-4040-4141", "010-4444-5555", "테스트");
        receivedMessageRepository.save(expected);

        // when
        MessageDto actual = receivedMessageRepository.findById(expected.getId());

        // then
        assertThat(actual.getMessage_type()).isEqualTo(expected.getMessage_type());
        assertThat(actual.getReceived_time()).isEqualTo(expected.getReceived_time());
        assertThat(actual.getSender_agent_id()).isEqualTo(expected.getSender_agent_id());
        assertThat(actual.getFrom_phone_number()).isEqualTo(expected.getFrom_phone_number());
        assertThat(actual.getTo_phone_number()).isEqualTo(expected.getTo_phone_number());
        assertThat(actual.getMessage_content()).isEqualTo(expected.getMessage_content());
    }

    @Test
    @DisplayName("COUNT")
    void should_return_number_of_count() throws SelectException, InsertException {
        // given
        receivedMessageRepository.save(new MessageDto(0, MessageType.SMS, receiveTime, 4, "010-4040-4141", "010-4444-5555", "테스트"));

        // when
        int result = receivedMessageRepository.count();

        // then
        assertThat(result).isEqualTo(1);
    }

    @Test
    @DisplayName("DELETE")
    void should_delete_given_dto() throws InsertException, DeleteException {
        // given
        MessageDto expected = new MessageDto(0, MessageType.SMS, receiveTime, 4, "010-4040-4141", "010-4444-5555", "테스트");
        receivedMessageRepository.save(expected);

        // when
        boolean actual = receivedMessageRepository.delete(expected);

        // then
        assertThat(actual).isTrue();

    }

    @Test
    @DisplayName("DELETE BY ID")
    void should_delete_by_id() throws InsertException, DeleteException {
        // given
        MessageDto expected = new MessageDto(0, MessageType.SMS, receiveTime, 4, "010-4040-4141", "010-4444-5555", "테스트");
        receivedMessageRepository.save(expected);

        // when
        boolean actual = receivedMessageRepository.deleteById(expected.getId());

        // then
        assertThat(actual).isTrue();
//        assertThat(receivedMessageRepository.findById(expected.getId())).isNull();

    }

    @Test
    @DisplayName("DELETE ALL")
    void should_delete_all() throws DeleteException {
        // given -> 100 칼럼 생성
        List<MessageDto> expected = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            expected.add(new MessageDto(0, MessageType.SMS, receiveTime, i, "010-4040-4141" + i, "010-4444-5555" + i, "테스트" + i));
        }
        System.out.println("\n" + expected + "\n");
        // when
        boolean actual = receivedMessageRepository.deleteAll();

        // then
        assertThat(actual).isTrue();
    }

    @Test
    @DisplayName("DELETE ALL BY CONDITION")
    void should_delete_all_when_request_condition() {

    }

    @Test
    @DisplayName("EXISTS BY ID")
    void should_exists_by_id() throws InsertException, SelectException {
        // given
        MessageDto expected = new MessageDto(0, MessageType.SMS, receiveTime, 4, "010-4040-4141", "010-4444-5555", "테스트");
        receivedMessageRepository.save(expected);

        // when
        boolean actual = receivedMessageRepository.existsById(1);

        // then
        assertThat(actual).isTrue();

    }


    @Test
    @DisplayName("FIND BY ID")
    void should_return_result_when_select_message_id() throws InsertException, SelectException {
        // given
        MessageDto expected = new MessageDto(0, MessageType.SMS, receiveTime, 4, "010-4040-4141", "010-4444-5555", "테스트");
        receivedMessageRepository.save(expected);

        // when
        MessageDto actual = receivedMessageRepository.findById(expected.getId());

        // then
//        assertThat(actual.getId()).isEqualTo(expected.getId());
        assertThat(actual.getMessage_type()).isEqualTo(expected.getMessage_type());
        assertThat(actual.getReceived_time()).isEqualTo(expected.getReceived_time());
        assertThat(actual.getTo_phone_number()).isEqualTo(expected.getTo_phone_number());
        assertThat(actual.getFrom_phone_number()).isEqualTo(expected.getFrom_phone_number());
        assertThat(actual.getSender_agent_id()).isEqualTo(expected.getSender_agent_id());
        assertThat(actual.getMessage_content()).isEqualTo(expected.getMessage_content());
    }

    @Test
    @DisplayName("INSERT")
    void should_insert() throws InsertException {

        // given
        MessageDto expected = new MessageDto(0, MessageType.SMS, receiveTime, 4, "010-4040-4141", "010-4444-5555", "테스트");

        // when
        int acutal = receivedMessageRepository.save(expected);

        // then
        assertThat(acutal).isEqualTo(1);
    }

    @Test
    @DisplayName("SAVE ALL")
    void should_save_entire_list() throws InsertException {
        // given
        List<MessageDto> expected = new ArrayList<>();
        for (int i = 0; i < 50; i++) {
            expected.add(new MessageDto(0, MessageType.SMS, receiveTime, i, "010-4040-4141" + i, "010-4444-5555" + i, "테스트" + i));
        }
        System.out.println(expected);
        // when
        int actual = receivedMessageRepository.saveAll(expected);

        // then
        assertThat(actual).isEqualTo(expected.size());
    }

}



