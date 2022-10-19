package kr.nanoit.http;


import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.Reader;


class SqlSessionTest {

    @Test
    @DisplayName("SqlSessionTest")
    void should_return_table_value() throws IOException {
        String resource = "config/config.xml";
        Reader reader = Resources.getResourceAsReader(resource);
        SqlSessionFactory sessionFactory = new SqlSessionFactoryBuilder().build(reader);
        SqlSession sqlSession = sessionFactory.openSession();

        long test = 1;

        String testValue = sqlSession.selectOne("getMsg",test);
        System.out.println(testValue);
    }

    @Test
    @DisplayName("DTO에 값을 넣어 테스트해보기 ")
    void should_return_success() throws IOException {
        String resource = "config/config.xml";
        Reader reader = Resources.getResourceAsReader(resource);
        SqlSessionFactory sessionFactory = new SqlSessionFactoryBuilder().build(reader);
        SqlSession sqlSession = sessionFactory.openSession();
    }

}
