package com.emlynma.java.base.sign;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class EncryptUtilsTests {

    private static final String SECRET_KEY;
    private static final String PUBLIC_KEY;
    private static final String PRIVATE_KEY;

    static {
        try {
            SECRET_KEY = Files.readString(Path.of("src/test/resources/sign/secret_key"));
            PUBLIC_KEY = formatKey(Files.readString(Path.of("src/test/resources/sign/public_key.pem")));
            PRIVATE_KEY = formatKey(Files.readString(Path.of("src/test/resources/sign/private_key.pem")));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void testGenerateAESKey() {
        System.out.println(EncryptUtils.generateAESKey());
    }

    @Test
    void testAES() {
        String data = "Hello my Java!";
        String encrypt = EncryptUtils.encryptAES(data, SECRET_KEY);
        System.out.println(encrypt);
        String decrypt = EncryptUtils.decryptAES(encrypt, SECRET_KEY);
        System.out.println(decrypt);
        Assertions.assertEquals(data, decrypt);
    }

    @Test
    void testGenerateRSAKeyPair() {
        String[] keyPair = EncryptUtils.generateRSAKeyPair();
        System.out.println(keyPair[0]);
        System.out.println(keyPair[1]);
    }

    @Test
    void testRSA() {
        String data = "Hello my Java!";
        String encrypt = EncryptUtils.encryptRSA(data, PUBLIC_KEY);
        System.out.println(encrypt);
        String decrypt = EncryptUtils.decryptRSA(encrypt, PRIVATE_KEY);
        System.out.println(decrypt);
        Assertions.assertEquals(data, decrypt);
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
