package kr.nanoit.repository;

import kr.nanoit.core.db.DataBaseSessionManager;
import kr.nanoit.model.message.ReceiveMessageDto;
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
            session.update("createTable");
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
    public boolean delete(long messageId) throws DeleteException {
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
    public boolean deleteAll() {
        try (SqlSession session = sessionManager.getSqlSession(true)) {
            session.delete("deleteAll");
            return true;
        }
    }

    @Override
    public Integer deleteAllByCondition(ReceiveMessageDto receiveMessageDto) {
        return null;
    }

    @Override
    public boolean existsById(long messageId) throws SelectException {
        try (SqlSession session = sessionManager.getSqlSession(true)) {
            int result = session.selectOne("existsById", messageId);
            if (result > 0) {
                return true;
            }
        }
        throw new SelectException("There are no data in Table");
    }

    @Override
    public Integer update(ReceiveMessageDto receiveMessageDto) throws UpdateException {
        try (SqlSession session = sessionManager.getSqlSession(true)) {
            if (receiveMessageDto != null) {
                int result = session.update("update", receiveMessageDto);
                if (result != 0) {
                    return result;
                }
                throw new UpdateException("The column you want to update does not exist in the table");
            }
            throw new UpdateException("Update Error");
        }
    }

    @Override
    public ReceiveMessageDto findById(long messageId) throws SelectException {
        try (SqlSession session = sessionManager.getSqlSession(true)) {
            if (messageId != 0) {
                ReceiveMessageDto receiveMessageDto = session.selectOne("findById", messageId);
                if (receiveMessageDto != null) {
                    return receiveMessageDto;
                }
                throw new SelectException("There is no value in the table");
            }
            throw new SelectException("There is no userId received");
        }
    }

    @Override
    public List<ReceiveMessageDto> findAll() throws SelectException {
        try (SqlSession session = sessionManager.getSqlSession(true)) {
            List<ReceiveMessageDto> list = session.selectList("selectAll");
            if (list != null) {
                return list;
            }
            throw new SelectException("The column you want to select does not exist in the table");
        }
    }

    @Override
    public ReceiveMessageDto findAllById(List<ReceiveMessageDto> list) {
        return null;
    }


    @Override
    public Integer save(ReceiveMessageDto receiveMessageDto) throws InsertException {
        try (SqlSession session = sessionManager.getSqlSession(true)) {
            int result = session.insert("save", receiveMessageDto);
            if (result > 0) {
                return result;
            }
            throw new InsertException("The column you want to select does not exist in the table");
        }
    }

    @Override
    public Integer saveAll(List<ReceiveMessageDto> list) throws InsertException {
        try (SqlSession session = sessionManager.getSqlSession(true)) {
            if (list.size() != 0 && list != null) {
                int result = session.insert("saveAll", list);
                if (list.size() == result) {
                    return result;
                }
            }
            throw new InsertException("InsertAll Error");
        } catch (InsertException e) {
            throw new InsertException(e.getReason());
        }
    }
}
