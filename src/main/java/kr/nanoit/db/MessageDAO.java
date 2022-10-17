package kr.nanoit.db;

import kr.nanoit.exception.message.DeleteException;
import kr.nanoit.exception.message.InsertException;
import kr.nanoit.exception.message.SelectException;
import kr.nanoit.exception.message.UpdateException;
import kr.nanoit.model.message.MessageDto;

import java.io.IOException;

/*
 Mybatis Mapper에 있는 쿼리 정보와 1:1 매칭
 */

public interface MessageDAO {
    static MessageDAO createMybatis() throws IOException {
        return new MessageDAOImpl();
    }

    MessageDto selectAll();

    MessageDto selectById(long messageId) throws SelectException;

    Integer insert(MessageDto messageDto) throws InsertException;

    MessageDto update(MessageDto messageDto) throws UpdateException;

    boolean delete(long messageId) throws DeleteException;

}
