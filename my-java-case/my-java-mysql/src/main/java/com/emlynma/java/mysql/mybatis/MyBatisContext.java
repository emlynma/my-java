package com.emlynma.java.mysql.mybatis;

import lombok.Getter;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import java.io.IOException;

@Getter
public class MyBatisContext {

    private final SqlSessionFactory sqlSessionFactory;

    public MyBatisContext() {
        try {
            sqlSessionFactory = new SqlSessionFactoryBuilder()
                    .build(Resources.getResourceAsStream("mybatis-config.xml"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static MyBatisContext getInstance() {
        return MyBatisContextHolder.INSTANCE;
    }

    private static class MyBatisContextHolder {
        private static final MyBatisContext INSTANCE = new MyBatisContext();
    }

}
