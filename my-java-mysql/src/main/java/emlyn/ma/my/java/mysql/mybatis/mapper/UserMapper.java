package emlyn.ma.my.java.mysql.mybatis.mapper;

import emlyn.ma.my.java.mysql.domain.User;

import java.util.List;

public interface UserMapper {

    User selectOneById(long id);

    List<User> selectList(User condition);

    int insert(User user);

    int update(User user, User condition);

    int delete(User condition);

}
