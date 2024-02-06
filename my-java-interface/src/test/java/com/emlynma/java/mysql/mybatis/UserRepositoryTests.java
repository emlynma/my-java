package com.emlynma.java.mysql.mybatis;

import com.emlynma.java.mysql.domain.User;
import com.emlynma.java.mysql.mybatis.MyBatisContext;
import com.emlynma.java.mysql.mybatis.repository.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class UserRepositoryTests {

    private static final UserRepository userRepository;

    static  {
        MyBatisContext myBatisContext = MyBatisContext.getInstance();
        userRepository = new UserRepository(myBatisContext.getSqlSessionFactory());
    }

    @Test
    public void testSelectByUid() {
        User user = userRepository.selectOneByUid(1710121088L);
        System.out.println(user);
        Assertions.assertNotNull(user);
    }

}
