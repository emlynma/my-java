package com.emlynma.java.mysql.jdbc;

import com.emlynma.java.base.json.JsonUtils;
import com.emlynma.java.mysql.domain.Sex;
import com.emlynma.java.mysql.domain.User;

import java.util.List;

public class UserRepository {

    private final JdbcMapper<User> userMapper = (rs) -> {
        User user = new User();
        user.setId(rs.getLong("id"));
        user.setUid(rs.getLong("uid"));
        user.setUname(rs.getString("uname"));
        user.setPhone(rs.getString("phone"));
        user.setEmail(rs.getString("email"));
        user.setAvatar(rs.getString("avatar"));
        user.setBirthday(rs.getDate("birthday"));
        user.setSex(Sex.valueOf(rs.getInt("sex")));
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
        String sql = "insert into user values (default, ?, ?, ?, default, default, default, ?, ?, default, default)";
        return BaseJdbcUtils.executeUpdate(sql, user.getUid(), user.getUname(), user.getPhone(), user.getSex().getCode(), JsonUtils.toJson(user.getExtraInfo()));
    }

    public int update(User user, User condition) {
        return 0;
    }

    public int delete(User condition) {
        return 0;
    }

}
