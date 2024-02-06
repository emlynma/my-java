package com.emlynma.java.base.security;

import org.junit.jupiter.api.Test;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * 摘要算法 MD5、SHA
 */
public class HashTest {

    @Test
    void testMD5() {
        System.out.println(encryptMD5("test"));
    }

    @Test
    void testSHA() {
        System.out.println(encryptSHA("test"));
    }

    public String encryptMD5(String originalString) {
        try {
            // 将原始字符串转换为字节数组
            byte[] originalBytes = originalString.getBytes();

            // 加密
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(originalBytes);
            byte[] encryptedBytes = md.digest();

            // 将加密后的字节数组转换为十六进制字符串
            StringBuilder sb = new StringBuilder();
            for (byte b : encryptedBytes) {
                sb.append(String.format("%02x", b));
            }
            return sb.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return null;
    }

    public String encryptSHA(String originalString) {
        try {
            // 将原始字符串转换为字节数组
            byte[] originalBytes = originalString.getBytes();

            // 加密
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            md.update(originalBytes);
            byte[] encryptedBytes = md.digest();

            // 将加密后的字节数组转换为十六进制字符串
            StringBuilder sb = new StringBuilder();
            for (byte b : encryptedBytes) {
                sb.append(String.format("%02x", b));
            }
            return sb.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return null;
    }

}
