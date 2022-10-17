package kr.nanoit.db;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.*;

import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;


// 다중 쓰레드가 접근 시 session 인스턴스를 새로 생성하여 나눠줘야됨

public class DataBaseSessionManager {
    private SqlSessionFactory sqlSessionFactory;

    public DataBaseSessionManager() throws IOException {
        String resource = "config.xml";
        Reader reader = Resources.getResourceAsReader(resource);
        sqlSessionFactory = new SqlSessionFactoryBuilder().build(reader);
    }

    public SqlSession getSqlSession(boolean autoCommit) {
        return sqlSessionFactory.openSession(autoCommit);
    }
}
