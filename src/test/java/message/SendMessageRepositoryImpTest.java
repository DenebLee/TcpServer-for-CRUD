package message;

import kr.nanoit.model.message.SendMessageDto;
import kr.nanoit.old.exception.message.DeleteException;
import kr.nanoit.old.exception.message.InsertException;
import kr.nanoit.old.exception.message.SelectException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;


@Testcontainers
class SendMessageRepositoryImpTest extends TestSetUp {

    public SendMessageRepositoryImpTest() throws IOException {
        super("SEND");
    }

    @BeforeEach
    void setUp() throws DeleteException {
        sendToTelecomMessageRepository.deleteAll();
    }

    @Test
    @DisplayName("SEND -> FIND BY ID")
    void should_send_find_by_id() throws SelectException {
        // given
        SendMessageDto expected = new SendMessageDto(0, 0, 0, "전송 성공");
        sendToTelecomMessageRepository.save(expected);
        long id = expected.getId();

        // when
        SendMessageDto actual = sendToTelecomMessageRepository.findById(id);

        // then
        assertThat(actual.getStatus()).isEqualTo(expected.getStatus());
        assertThat(actual.getReceived_id()).isEqualTo(expected.getReceived_id());
        assertThat(actual.getResult()).isEqualTo(expected.getResult());
    }

    @Test
    @DisplayName("SEND -> COUNT")
    void should_send_count_by_id() throws SelectException {
        // given
        SendMessageDto expected = new SendMessageDto(0, 0, 0, "count테스트용");
        sendToTelecomMessageRepository.save(expected);

        // when
        int actual = sendToTelecomMessageRepository.count();

        // then
        assertThat(actual).isEqualTo(1);
    }

    @Test
    @DisplayName("SEND -> DELETE BY ID")
    void should_send_delete_by_id() throws SelectException {
        // given
        SendMessageDto expected = new SendMessageDto(0, 0, 0, "삭제 용 데이터");
        sendToTelecomMessageRepository.save(expected);
        long id = expected.getId();

        // when
        boolean actual = sendToTelecomMessageRepository.delete(id);

        // then
        assertThat(actual).isTrue();
    }

    @Test
    @DisplayName("SEND -> DELETE ALL")
    void should_send_delete_all() throws InsertException, DeleteException {
        // given
        List<SendMessageDto> expected = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            expected.add(new SendMessageDto(0, i, i, "삭제 용 데이터"));
        }
        sendToTelecomMessageRepository.saveAll(expected);

        //  when
        boolean actual = sendToTelecomMessageRepository.deleteAll();

        // then
        assertThat(actual).isTrue();
    }

    @Test
    @DisplayName("SEND -> SAVE")
    void should_send_save() throws SelectException {

        // given
        SendMessageDto sendMessageDto = new SendMessageDto(0, 0, 0, "테스트 벨류");

        // when
        int actual = sendToTelecomMessageRepository.save(sendMessageDto);

        // then
        assertThat(actual).isEqualTo(1);
    }

    @Test
    @DisplayName("SEND -> SAVE ALL")
    void should_send_save_all() throws InsertException {
        // givne
        List<SendMessageDto> expected = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            expected.add(new SendMessageDto(0, i, i, "저장 전용 데이터"));
        }

        // when
        int actual = sendToTelecomMessageRepository.saveAll(expected);

        // then
        assertThat(actual).isEqualTo(expected.size());
    }
}
