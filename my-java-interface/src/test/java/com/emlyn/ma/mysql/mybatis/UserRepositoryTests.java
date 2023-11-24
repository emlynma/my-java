package com.emlyn.ma.mysql.mybatis;

import com.emlyn.ma.mysql.domain.User;
import com.emlyn.ma.mysql.mybatis.repository.UserRepository;
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
