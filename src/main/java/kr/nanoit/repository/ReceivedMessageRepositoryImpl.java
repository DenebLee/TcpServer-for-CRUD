package kr.nanoit.repository;

import kr.nanoit.core.db.DataBaseSessionManager;
import kr.nanoit.model.message.ReceiveMessage;
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
    public Integer deleteAllByCondition(ReceiveMessage receiveMessage) {
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
    public Integer update(ReceiveMessage receiveMessage) throws UpdateException {
        try (SqlSession session = sessionManager.getSqlSession(true)) {
            if (receiveMessage != null) {
                int result = session.update("update", receiveMessage);
                if (result > 0) {
                    return result;
                }
                throw new UpdateException("The column you want to update does not exist in the table");
            }
            throw new UpdateException("Update Error");
        }
    }

    @Override
    public ReceiveMessage findById(long messageId) throws SelectException {
        try (SqlSession session = sessionManager.getSqlSession(true)) {
            if (messageId != 0) {
                ReceiveMessage receiveMessage = session.selectOne("findById", messageId);
                if (receiveMessage != null) {
                    return receiveMessage;
                }
                throw new SelectException("There is no value in the table");
            }
            throw new SelectException("There is no userId received");
        }
    }

    @Override
    public List<ReceiveMessage> findAll() throws SelectException {
        try (SqlSession session = sessionManager.getSqlSession(true)) {
            List<ReceiveMessage> list = session.selectList("selectAll");
            if (list != null) {
                return list;
            }
            throw new SelectException("The column you want to select does not exist in the table");
        }
    }

    @Override
    public List<ReceiveMessage> findAllByStatus(Integer status) throws SelectException {
        try (SqlSession session = sessionManager.getSqlSession(true)) {
            List<ReceiveMessage> list = session.selectList("selectAllByStatus", status);
            if (list != null) {
                return list;
            }
            throw new SelectException("The colum you want to select does not exist int the table");
        }
    }


    @Override
    public Integer save(ReceiveMessage receiveMessage) throws InsertException {
        try (SqlSession session = sessionManager.getSqlSession(true)) {
            int result = session.insert("save", receiveMessage);
            if (result > 0) {
                return result;
            }
            throw new InsertException("The column you want to select does not exist in the table");
        }
    }

    @Override
    public Integer saveAll(List<ReceiveMessage> list) throws InsertException {
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
