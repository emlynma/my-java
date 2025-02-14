package com.emlynma.java.mysql.jdbc;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import lombok.extern.slf4j.Slf4j;

import javax.sql.DataSource;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

@Slf4j
public abstract class PooledJdbcUtils {

    private static final DataSource dataSource;

    static {
        try {
            Properties hikariProperties = new Properties();
            hikariProperties.load(BaseJdbcUtils.class.getClassLoader().getResourceAsStream("hikari.properties"));
            HikariConfig config = new HikariConfig(hikariProperties);
            dataSource = new HikariDataSource(config);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static Connection getConnection() throws SQLException {
        return dataSource.getConnection();
    }

    /**
     * DQL (select)
     */
    public static <T> List<T> executeQuery(String sql, JdbcMapper<T> mapper, Object... params) {
        try (var connection = getConnection(); var statement = connection.prepareStatement(sql)) {
            for (var i = 0; i < params.length; i++) {
                statement.setObject(i + 1, params[i]);
            }
            var resultSet = statement.executeQuery();
            List<T> list = new ArrayList<>();
            while (resultSet.next()) {
                list.add(mapper.map(resultSet));
            }
            return list;
        } catch (SQLException e) {
            log.error("query error", e);
            return new ArrayList<>();
        }
    }

}
