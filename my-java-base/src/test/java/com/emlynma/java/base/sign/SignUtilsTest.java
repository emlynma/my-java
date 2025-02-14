package com.emlynma.java.base.sign;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SignUtilsTest {

    private static final String PUBLIC_KEY;
    private static final String PRIVATE_KEY;

    static  {
        try {
            PUBLIC_KEY = formatKey(Files.readString(Path.of("src/test/resources/sign/public_key.pem")));
            PRIVATE_KEY = formatKey(Files.readString(Path.of("src/test/resources/sign/private_key.pem")));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void testSign() {
        Map<String, Object> params = new HashMap<>();
        params.put("name", "emlyn");
        params.put("age", 24);
        params.put("isMale", true);
        params.put("skills", List.of("Java", "Python", "JavaScript"));
        params.put("company", null);

        String sign = SignUtils.sign(params, PRIVATE_KEY);
        System.out.println(sign);
        boolean verify = SignUtils.verify(params, sign, PUBLIC_KEY);
        System.out.println(verify);
    }

    private static String formatKey(String key) {
        if (key == null || key.isEmpty()) {
            return key;
        }
        return key
                .replace("-----BEGIN PRIVATE KEY-----", "")
                .replace("-----END PRIVATE KEY-----", "")
                .replace("-----BEGIN PUBLIC KEY-----", "")
                .replace("-----END PUBLIC KEY-----", "")
                .replaceAll("\n", "");
    }

}
