package com.emlynma.java.mysql.mybatis.type;

import com.emlynma.java.base.json.JsonUtils;
import com.emlynma.java.mysql.domain.User;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.MappedTypes;
import org.apache.ibatis.type.TypeHandler;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

@MappedTypes(User.ExtraInfo.class)
public class ExtraInfoTypeHandler implements TypeHandler<User.ExtraInfo> {

    @Override
    public void setParameter(PreparedStatement ps, int i, User.ExtraInfo parameter, JdbcType jdbcType) throws SQLException {
        throw new UnsupportedOperationException();
    }

    @Override
    public User.ExtraInfo getResult(ResultSet rs, String columnName) throws SQLException {
        return JsonUtils.toObject(rs.getString(columnName), User.ExtraInfo.class);
    }

    @Override
    public User.ExtraInfo getResult(ResultSet rs, int columnIndex) throws SQLException {
        return JsonUtils.toObject(rs.getString(columnIndex), User.ExtraInfo.class);
    }

    @Override
    public User.ExtraInfo getResult(CallableStatement cs, int columnIndex) throws SQLException {
        return JsonUtils.toObject(cs.getString(columnIndex), User.ExtraInfo.class);
    }

}
