package com.emlyn.ma.mysql.jdbc;

import java.sql.ResultSet;
import java.sql.SQLException;

@FunctionalInterface
public interface JdbcMapper<T> {

    T map(ResultSet resultSet) throws SQLException;

}
