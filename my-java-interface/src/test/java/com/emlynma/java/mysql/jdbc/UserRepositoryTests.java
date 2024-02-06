package com.emlynma.java.mysql.jdbc;

import com.emlynma.java.mysql.jdbc.UserRepository;
import org.junit.jupiter.api.Test;

public class UserRepositoryTests {

    @Test
    void testSelectList() {
        UserRepository userRepository = new UserRepository();
        System.out.println(userRepository.selectList(null));
    }

    @Test
    void testSelectOne() {
        UserRepository userRepository = new UserRepository();
        System.out.println(userRepository.selectOne(1001));
    }

}
