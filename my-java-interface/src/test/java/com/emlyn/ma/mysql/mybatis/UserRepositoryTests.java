package com.emlyn.ma.mysql.mybatis;

import com.emlyn.ma.mysql.mybatis.repository.UserRepository;
import org.junit.jupiter.api.Test;

public class UserRepositoryTests {

    private static final UserRepository userRepository;

    static  {
        MyBatisContext myBatisContext = MyBatisContext.getInstance();
        userRepository = new UserRepository(myBatisContext.getSqlSessionFactory());
    }

    @Test
    public void testSelectByUid() {
        System.out.println(userRepository.selectOneByUid(11710121088L));
    }

}
