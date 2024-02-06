package com.emlynma.java.common.sign;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public abstract class HashUtils {

    public static String encryptMD5(String content) {
        return generateHash(content, "MD5");
    }

    public static String encryptSHA(String content) {
        return generateHash(content, "SHA-256");
    }

    private static String generateHash(String content, String algorithm) {
        try {
            MessageDigest digest = MessageDigest.getInstance(algorithm);
            byte[] hashBytes = digest.digest(content.getBytes());
            // 将字节数组转换为十六进制字符串
            StringBuilder hexString = new StringBuilder();
            for (byte b : hashBytes) {
                String hex = Integer.toHexString(0xff & b);
                if (hex.length() == 1) {
                    hexString.append('0');
                }
                hexString.append(hex);
            }
            return hexString.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("hash algorithm not supported", e);
        }
    }

}
