package kr.nanoit.repository;

import kr.nanoit.model.message.SendMessageDto;
import kr.nanoit.old.exception.message.DeleteException;
import kr.nanoit.old.exception.message.InsertException;
import kr.nanoit.old.exception.message.SelectException;
import kr.nanoit.old.exception.message.UpdateException;

import java.io.IOException;
import java.util.List;
import java.util.Properties;

public interface SendToTelecomMessageRepository {
    static SendToTelecomMessageRepository createSendMybatis(Properties properties) throws IOException {
        return new SendToTelecomMessageRepositoryImpl(properties);
    }

    Integer count() throws SelectException;

    boolean delete(long id);

    boolean deleteAll() throws DeleteException;

    Integer save(SendMessageDto sendMessageDto) throws SelectException;

    Integer saveAll(List<SendMessageDto> list) throws InsertException;

    Integer update(SendMessageDto sendMessageDto) throws UpdateException;

    SendMessageDto findById(long id) throws SelectException;
}
