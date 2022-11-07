package kr.nanoit.repository;

import kr.nanoit.core.db.DataBaseSessionManager;
import kr.nanoit.model.message.SendMessage;
import kr.nanoit.exception.message.InsertException;
import kr.nanoit.exception.message.SelectException;
import kr.nanoit.exception.message.UpdateException;
import org.apache.ibatis.session.SqlSession;

import java.io.IOException;
import java.util.List;
import java.util.Properties;

public class SendToTelecomMessageRepositoryImpl implements SendToTelecomMessageRepository {
    private final DataBaseSessionManager sessionManager;

    public SendToTelecomMessageRepositoryImpl(Properties properties) throws IOException {
        sessionManager = new DataBaseSessionManager(properties);
        createSendTable();
    }

    private void createSendTable() {
        try (SqlSession session = sessionManager.getSqlSession(true)) {
            session.update("send_createTable");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public Integer count() throws SelectException {
        try (SqlSession session = sessionManager.getSqlSession(true)) {
            int count = session.selectOne("send_count");
            if (count != 0) {
                return count;
            }
            throw new SelectException("The counted columns do no exits on the table");
        } catch (SelectException e) {
            throw new SelectException(e.getReason());
        }
    }

    @Override
    public boolean deleteById(long id) {
        try (SqlSession session = sessionManager.getSqlSession(true)) {
            if (id != 0) {
                int result = session.delete("send_deleteById", id);
                if (result > 0) {
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public boolean deleteAll() {
        try (SqlSession session = sessionManager.getSqlSession(true)) {
            session.delete("send_deleteAll");
            return true;
        }
    }

    @Override
    public Integer save(SendMessage sendMessage) throws SelectException {
        try (SqlSession session = sessionManager.getSqlSession(true)) {
            int result = session.insert("send_save", sendMessage);
            if (result > 0) {
                return result;
            }
            throw new SelectException("The column you want to select does not exist in the table");
        }
    }

    @Override
    public Integer saveAll(List<SendMessage> list) throws InsertException {
        try (SqlSession session = sessionManager.getSqlSession(true)) {
            if (list.size() != 0 && list != null) {
                int result = session.insert("send_saveAll", list);
                if (result == list.size()) {
                    return result;
                }
            }
            throw new InsertException("InsertAll Error");
        } catch (InsertException e) {
            throw new InsertException(e.getReason());
        }
    }

    @Override
    public Integer update(SendMessage sendMessage) throws UpdateException {
        try (SqlSession session = sessionManager.getSqlSession(true)) {
            int result = session.update("send_update", sendMessage);
            if (result > 0) {
                return result;
            } else {
                throw new UpdateException("왜 안되는건데 " + result);
            }
        }
    }

    @Override
    public SendMessage findById(long id) throws SelectException {
        try (SqlSession session = sessionManager.getSqlSession(true)) {
            SendMessage dto = session.selectOne("send_findById", id);
            if (dto != null) {
                return dto;
            }
        }
        throw new SelectException("FindById error");
    }

    @Override
    public List<SendMessage> findAll() throws SelectException {
        try (SqlSession session = sessionManager.getSqlSession(true)) {
            List<SendMessage> list = session.selectList("send_findAll");
            if (list != null) {
                return list;
            }
        }
        throw new SelectException("There's no data on the table you're trying to find");
    }
}
