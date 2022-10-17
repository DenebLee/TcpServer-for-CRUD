package kr.nanoit.db;

import kr.nanoit.exception.message.DeleteException;
import kr.nanoit.exception.message.InsertException;
import kr.nanoit.exception.message.SelectException;
import kr.nanoit.exception.message.UpdateException;
import kr.nanoit.model.message.MessageDto;
import org.apache.ibatis.session.SqlSession;

import java.io.IOException;

public class MessageDAOImpl implements MessageDAO {
    private DataBaseSessionManager sessionManager;
    private SqlSession sqlSession;

    public MessageDAOImpl() throws IOException {
        sessionManager = new DataBaseSessionManager();
        sqlSession = sessionManager.getSqlSession(true);
        createTable();
    }

    private void createTable() {
        try {
            sqlSession.update("createTable");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public Integer insert(MessageDto messageDto) throws InsertException {
        try (SqlSession session = sessionManager.getSqlSession(true)) {
            int result = session.insert("insert", messageDto);
            if (result > 0) {
                return result;
            }
            throw new InsertException("Insert data error");
        }
    }


    @Override
    public MessageDto selectById(long messageId) throws SelectException {
        try (SqlSession session = sessionManager.getSqlSession(true)) {
            if (messageId != 0) {
                MessageDto messageDto = session.selectOne("selectById", messageId);
                if (messageDto != null) {
                    return messageDto;
                }
                throw new SelectException("There is no value in the table");
            }
            throw new SelectException("There is no userId received");
        }
    }

    @Override
    public boolean delete(long messageId) throws DeleteException {
        try (SqlSession session = sessionManager.getSqlSession(true)) {
            if (messageId != 0) {
                int result_int = sqlSession.delete("delete", messageId);
                if (result_int != 0) {
                    return true;
                }
                throw new DeleteException("The column you want to delete does not exist in the table");
            }
            throw new DeleteException("There is no userId received");
        }
    }

    @Override
    public MessageDto update(MessageDto messageDto) throws UpdateException {
        try (SqlSession session = sessionManager.getSqlSession(true)) {
            if (messageDto != null) {
                int result_int = sqlSession.update("update", messageDto);
                if (result_int != 0) {
                    messageDto = sqlSession.selectOne("selectById", messageDto.getId());
                    return messageDto;
                }
                throw new UpdateException("The column you want to update does not exist in the table");
            }
            throw new UpdateException("Update Error");
        }
    }

    @Override
    public MessageDto selectAll() {
        return null;
    }


}
