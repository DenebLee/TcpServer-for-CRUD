package kr.nanoit.repository;

import kr.nanoit.old.exception.message.DeleteException;
import kr.nanoit.old.exception.message.InsertException;
import kr.nanoit.old.exception.message.SelectException;
import kr.nanoit.old.exception.message.UpdateException;
import kr.nanoit.model.message.ReceiveMessage;

import java.io.IOException;
import java.util.List;
import java.util.Properties;

/*

    count -  사용 가능한 엔티티 수를 반환 ✔
    delete (entity) - 지정된 엔티티를 삭제 ✔
    deleteAll () - 리포지토리에서 관리하는 모든 엔티티를 삭제
    deleteAllByCondition(Iterable<? extends ID> ids) - 지정된 ID를 가진 T 유형의 모든 인스턴스를 삭제
    deleteById(ID id) - 지정된 ID의 엔티티를 삭제
    update(entity) - 지정된 id의 엔티티 수정
    existsById(ID id) - 지정된 ID를 가진 엔티티가 있는지 여부를 반환
    findAll() - 형식의 모든 인스턴스를 반환 ✔
    findAllById(Iterable<ID> ids) - 지정된 ID를 가진 T 유형의 모든 인스턴스를 반환
    findById(ID id) - ID를 기준으로 엔티티를 검색 ✔
    save(S entity) - 지정된 엔티티 저장 ✔
    saveAll(Iterable<S> entities) - 지정된 모든 엔티티 저장 ✔

 */

public interface ReceivedMessageRepository {
    static ReceivedMessageRepository createMybatis(Properties properties) throws IOException {
        return new ReceivedMessageRepositoryImpl(properties);
    }

    Integer count() throws SelectException;

    boolean delete(long messageId) throws DeleteException;

    boolean deleteAll() throws DeleteException;

    Integer deleteAllByCondition(ReceiveMessage receiveMessage);

    Integer update(ReceiveMessage receiveMessage) throws UpdateException;

    boolean existsById(long messageId) throws SelectException;

    ReceiveMessage findById(long messageId) throws SelectException;

    List<ReceiveMessage> findAll() throws SelectException;

    ReceiveMessage findAllById(List<ReceiveMessage> list);

    Integer save(ReceiveMessage receiveMessage) throws InsertException;

    Integer saveAll(List<ReceiveMessage> list) throws InsertException;


}
