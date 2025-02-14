package com.emlynma.java.mysql.mybatis.mapper;

import com.emlynma.java.mysql.domain.User;

import java.util.List;

public interface UserMapper {

    User selectById(long id);

    List<User> selectList(User condition);

    int insert(User user);

    int update(User user, User condition);

    int delete(User condition);

}
