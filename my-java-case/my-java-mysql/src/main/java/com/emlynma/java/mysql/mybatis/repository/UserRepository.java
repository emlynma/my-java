package com.emlynma.java.mysql.mybatis.repository;

import com.emlynma.java.mysql.domain.User;
import com.emlynma.java.mysql.mybatis.mapper.UserMapper;
import org.apache.ibatis.session.SqlSessionFactory;

import java.util.List;

public class UserRepository {

    private final SqlSessionFactory sqlSessionFactory;

    public UserRepository(SqlSessionFactory sqlSessionFactory) {
        this.sqlSessionFactory = sqlSessionFactory;
    }

    public User selectOneByUid(long uid) {
        try (var sqlSession = sqlSessionFactory.openSession()) {
            UserMapper userMapper = sqlSession.getMapper(UserMapper.class);
            User condition = new User();
            condition.setUid(uid);
            List<User> users = userMapper.selectList(condition);
            return users.isEmpty() ? null : users.get(0);
        }
    }

}
