package com.emlyn.ma.mysql.mybatis.mapper;

import com.emlyn.ma.mysql.domain.User;

import java.util.List;

public interface UserMapper {

    User selectById(long id);

    User selectOne(User condition);

    List<User> selectList(User condition);

    int insert(User user);

    int update(User user, User condition);

    int delete(User condition);

}
