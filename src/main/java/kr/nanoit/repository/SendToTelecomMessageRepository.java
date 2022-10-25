package kr.nanoit.repository;

import kr.nanoit.model.message.SendMessage;
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

    boolean deleteById(long id);

    boolean deleteAll() throws DeleteException;

    Integer save(SendMessage sendMessage) throws SelectException;

    Integer saveAll(List<SendMessage> list) throws InsertException;

    Integer update(SendMessage sendMessage) throws UpdateException;

    SendMessage findById(long id) throws SelectException;
}
