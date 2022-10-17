package message;

import kr.nanoit.db.MessageDAOImpl;
import kr.nanoit.exception.message.DeleteException;
import kr.nanoit.exception.message.InsertException;
import kr.nanoit.exception.message.SelectException;
import kr.nanoit.exception.message.UpdateException;
import kr.nanoit.model.message.MessageDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Locale;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("MessageDAO Method Test")
class MessageDAOImplTest {
    MessageDAOImpl messageDAO;
    private Timestamp createdAt;

    @BeforeEach
    void setUp() throws IOException {
        messageDAO = new MessageDAOImpl();
        String currentTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.KOREA).format(System.currentTimeMillis());
        createdAt = Timestamp.valueOf(currentTime);
    }

    @Test
    @DisplayName("MessageDAO -> insert")
    void should_return_one_when_insert_messageDTO() throws InsertException {
        // given
        MessageDto expected = new MessageDto(0, createdAt, "이정섭", "안녕하세요");

        // when
        int acutal = messageDAO.insert(expected);


        // then
        assertThat(acutal).isEqualTo(1);
    }

    @Test
    @DisplayName("MessageDAO -> select")
    void should_return_result_when_select_message_id() throws InsertException, SelectException {
        // given
        MessageDto expected = new MessageDto(0, createdAt, "이정섭", "안녕하세요");
        messageDAO.insert(expected);

        // when
        MessageDto actual = messageDAO.selectById(expected.getId());
        System.out.println(actual);

        // then
        assertThat(actual.getId()).isEqualTo(expected.getId());
        assertThat(actual.getSender()).isEqualTo(expected.getSender());
        assertThat(actual.getContent()).isEqualTo(expected.getContent());
        assertThat(actual.getSend_time()).isEqualTo(expected.getSend_time());
    }

    @Test
    @DisplayName("MessageDAO -> delete")
    void should_return_result_when_delete_message_id() throws InsertException, SelectException, DeleteException {
        // given
        MessageDto expected = new MessageDto(0, createdAt, "이정섭", "안녕하세요");
        messageDAO.insert(expected);

        // when
        boolean actual = messageDAO.delete(expected.getId());

        // then
        assertThat(actual).isTrue();

    }

    @Test
    @DisplayName("MessageDAO -> update")
    void should_return_result_when_update_message_id() throws InsertException, SelectException, UpdateException {
        // given
        MessageDto originalMessageDataExpected = new MessageDto(0, createdAt, "이정섭", "안녕하세요");
        messageDAO.insert(originalMessageDataExpected);
        MessageDto updateExpected = new MessageDto(originalMessageDataExpected.getId(), createdAt, "양선호", "하하하하하");

        // when
        MessageDto actual = messageDAO.update(updateExpected);

        // then
        assertThat(actual).isNotNull();
        assertThat(actual.getId()).isEqualTo(updateExpected.getId());
        assertThat(actual.getSender()).isEqualTo(updateExpected.getSender());
        assertThat(actual.getContent()).isEqualTo(updateExpected.getContent());
        assertThat(actual).isNotEqualTo(originalMessageDataExpected);
    }
}

