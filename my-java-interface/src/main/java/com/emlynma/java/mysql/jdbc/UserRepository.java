package com.emlynma.java.mysql.jdbc;

import com.emlynma.java.common.json.JsonUtils;
import com.emlynma.java.mysql.domain.Sex;
import com.emlynma.java.mysql.domain.User;

import java.util.List;

public class UserRepository {

    private final JdbcMapper<User> userMapper = (rs) -> {
        User user = new User();
        user.setId(rs.getLong("id"));
        user.setUid(rs.getLong("uid"));
        user.setName(rs.getString("name"));
        user.setEmail(rs.getString("email"));
        user.setPhone(rs.getString("phone"));
        user.setSex(Sex.valueOf(rs.getInt("sex")));
        user.setAge(rs.getInt("age"));
        user.setStatus(rs.getInt("status"));
        user.setCreateTime(rs.getTimestamp("create_time"));
        user.setUpdateTime(rs.getTimestamp("update_time"));
        user.setExtraInfo(JsonUtils.toObject(rs.getString("extra_info"), User.ExtraInfo.class));
        return user;
    };

    public User selectOne(Integer id) {
        String sql = "select * from user where id = ?";
        List<User> users = PooledJdbcUtils.executeQuery(sql, userMapper, id);
        return users.isEmpty() ? null : users.get(0);
    }

    public List<User> selectList(User condition) {
        String sql = "select * from user limit 100";
        return BaseJdbcUtils.executeQuery(sql, userMapper);
    }

    public int insert(User user) {
        String sql = "insert into user values (default, ?, ?, ?, default, default, ?, ?, ?, default, default)";
        return BaseJdbcUtils.executeUpdate(sql, user.getName(), user.getEmail(), user.getPhone(), user.getSex().getCode(), user.getAge(), JsonUtils.toJson(user.getExtraInfo()));
    }

    public int update(User user, User condition) {
        return 0;
    }

    public int delete(User condition) {
        return 0;
    }

}
