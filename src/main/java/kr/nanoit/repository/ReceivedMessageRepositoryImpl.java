package kr.nanoit.repository;

import kr.nanoit.core.db.DataBaseSessionManager;
import kr.nanoit.model.message.MessageDto;
import kr.nanoit.old.exception.message.DeleteException;
import kr.nanoit.old.exception.message.InsertException;
import kr.nanoit.old.exception.message.SelectException;
import kr.nanoit.old.exception.message.UpdateException;
import org.apache.ibatis.session.SqlSession;

import java.io.IOException;
import java.util.List;
import java.util.Properties;

public class ReceivedMessageRepositoryImpl implements ReceivedMessageRepository {
    private final DataBaseSessionManager sessionManager;


    public ReceivedMessageRepositoryImpl(Properties properties) throws IOException {
        sessionManager = new DataBaseSessionManager(properties);
        createReceiveTable();
    }

    private void createReceiveTable() {
        try (SqlSession session = sessionManager.getSqlSession(true)) {
            session.update("createReceivedTable");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public Integer count() throws SelectException {
        try {
            SqlSession session = sessionManager.getSqlSession(true);
            int count = session.selectOne("count");
            if (count != 0) {
                return count;
            }
            throw new SelectException("The counted columns do not exist on the table");
        } catch (SelectException e) {
            throw new SelectException(e.getReason());
        }
    }

    @Override
    public boolean deleteById(long messageId) throws DeleteException {
        try (SqlSession session = sessionManager.getSqlSession(true)) {
            if (messageId != 0) {
                int result = session.delete("deleteById", messageId);
                if (result != 0) {
                    return true;
                }
                throw new DeleteException("The column you want to delete does not exist in the table");
            }
            throw new DeleteException("There is no userId received");
        }
    }

    @Override
    public boolean delete(MessageDto messageDto) throws DeleteException {
        try (SqlSession session = sessionManager.getSqlSession(true)) {
            if (messageDto != null) {
                int result = session.delete("delete", messageDto);
                if (result != 0) {
                    return true;
                }
            }
        }
        throw new DeleteException("Delete use Dto Error");
    }

    @Override
    public boolean deleteAll() throws DeleteException {
        try (SqlSession session = sessionManager.getSqlSession(true)) {
            int result = session.delete("deleteAll");
            if (result == 0) {
                return true;
            } else {
                throw new DeleteException("삭제된 값이 존재하지 않음");
            }
        } catch (DeleteException e) {
            throw new DeleteException(e.getReason());
        }
    }

    @Override
    public Integer deleteAllByCondition(MessageDto messageDto) {
        return null;
    }

    @Override
    public boolean existsById(long messageId) throws SelectException {
        try (SqlSession session = sessionManager.getSqlSession(true)) {
            if (session.selectOne("existsById", messageId)) {
                return true;
            }
        }
        throw new SelectException("There are no data in Table");
    }

    @Override
    public MessageDto update(MessageDto messageDto) throws UpdateException {
        try (SqlSession session = sessionManager.getSqlSession(true)) {
            if (messageDto != null) {
                int result_int = session.update("update", messageDto);
                if (result_int != 0) {
                    messageDto = session.selectOne("selectById", messageDto.getId());
                    return messageDto;
                }
                throw new UpdateException("The column you want to update does not exist in the table");
            }
            throw new UpdateException("Update Error");
        }
    }

    @Override
    public MessageDto findById(long messageId) throws SelectException {
        try (SqlSession session = sessionManager.getSqlSession(true)) {
            if (messageId != 0) {
                MessageDto messageDto = session.selectOne("findById", messageId);
                if (messageDto != null) {
                    return messageDto;
                }
                throw new SelectException("There is no value in the table");
            }
            throw new SelectException("There is no userId received");
        }
    }

    @Override
    public List<MessageDto> findAll() throws SelectException {
        try (SqlSession session = sessionManager.getSqlSession(true)) {
            List<MessageDto> list = session.selectList("selectAll");
            if (list != null) {
                return list;
            }
            throw new SelectException("The column you want to select does not exist in the table");
        }
    }

    @Override
    public MessageDto findAllById(List<MessageDto> list) {
        return null;
    }


    @Override
    public Integer save(MessageDto messageDto) throws InsertException {
        try (SqlSession session = sessionManager.getSqlSession(true)) {
            int result = session.insert("save", messageDto);
            if (result > 0) {
                return result;
            }
            throw new InsertException("The column you want to select does not exist in the table");
        }
    }

    @Override
    public Integer saveAll(List<MessageDto> list) throws InsertException {
        try (SqlSession session = sessionManager.getSqlSession(true)) {
            int result = session.insert("saveAll", list);
            if (list.size() == result) {
                return result;
            }
            throw new InsertException("InsertAll Error");
        } catch (InsertException e) {
            throw new InsertException(e.getReason());
        }
    }
}
