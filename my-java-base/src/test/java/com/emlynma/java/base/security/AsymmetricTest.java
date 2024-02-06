package com.emlynma.java.base.security;

import org.junit.jupiter.api.Test;

import javax.crypto.Cipher;
import java.nio.charset.StandardCharsets;
import java.security.*;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

/**
 * 非对称加密算法 RSA
 */
public class AsymmetricTest {

    private static final String PublicKey = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEA9KA463kmFG/TGr0fmQgZNNJ5rnbEld8dgoRJX3lywK4rqX6q9lTJrm16Hc6OQf9XTy6TawD2FqwOUxajn+Tq99pGB1W+as1OHVoNJ5CtstSZ+cKQyPELZ5qW5SG9I3N2wzE/YCpIqMsVizFqTQnoe6PZaLPTguiShcSxDC7tJhhEPgvaV9olwdEox23ht7rHh7oha698XDNnPin+4E7OO6sJ7m0sZXZ7jJIs6dz5jzxOIql7A3RS84y1r6g5JO4jTyFDP5pTiCHT7uKTYpGJ41g23fYDbLdMgV4CVC8GN17QfD/ojXnOTY0n7CxItFpbsv/bhSjKv/eP6AK3KR4GeQIDAQAB";
    private static final String PrivateKey = "MIIEuwIBADANBgkqhkiG9w0BAQEFAASCBKUwggShAgEAAoIBAQD0oDjreSYUb9MavR+ZCBk00nmudsSV3x2ChElfeXLAriupfqr2VMmubXodzo5B/1dPLpNrAPYWrA5TFqOf5Or32kYHVb5qzU4dWg0nkK2y1Jn5wpDI8QtnmpblIb0jc3bDMT9gKkioyxWLMWpNCeh7o9los9OC6JKFxLEMLu0mGEQ+C9pX2iXB0SjHbeG3useHuiFrr3xcM2c+Kf7gTs47qwnubSxldnuMkizp3PmPPE4iqXsDdFLzjLWvqDkk7iNPIUM/mlOIIdPu4pNikYnjWDbd9gNst0yBXgJULwY3XtB8P+iNec5NjSfsLEi0Wluy/9uFKMq/94/oArcpHgZ5AgMBAAECgf84Jk4kCF2PMwuc0T+hD/tj1z77PJhvsodp/g8C8RhST/H4k70trUPzHm53HzixijownCdz+F3iqokbvAL3rSiU5RCqcoZGFuoLF+R5xS3iNzafb3cIPnVZ7jfZd0lk5CGuEYwtOVlM/7qSfu2Q9l7EBxdicRAxhWEoADZZhpDT1rw4Qrz+TjmCdOpJSMU0wSvgfNUFACLZQ4QoVoECYJ0gtmVaf/WtababLP7fLWSZ+5GGqxwYxOiOOxfRyIi8DNttfffnKn9J9nlMAUz8/MxsCDPN1soHdTbgTWpR7mwyVbhnzBC9iWegtJXiu1Dwqc29pxvy8ROSdJkXa9Mc7scCgYEA+Ik3jAGwycFUQIeult8OfjYbDL1OKNrGZP7g/I58h3aEHpMbtPpGJ94fUGs+GLjIK6ZXJkAllZPrQFP66s/UaVTN6+2lwjpSYUii+23xBemXWqKyZ1q8HwrvIv7TiRMi2XjrT0rZFgKYjRO8TPMljj8Y5pROdn+EAqAgFG6ptmsCgYEA+/jxknvsmCvBLK4wFQoJmrKKT3HShz6mCXVlpNPb9u9qG63DVhv9gUOgOKjOMnAaz1GWgNeJtURLrircN9fnApECyNBR7HqcQtTotAcnFijOGEUhqlernokA0xWPM5TV8kHRH+WOScIV/7Xe53XcDxAG+FMCGFFToK5WyOfcx6sCgYEAu4GqqrJL0qtNCTJUgfbfVFE1rzLTWPdVXrT8wQ4u3XSI0RhjevKnI0TtkxqVysj+HBBvB6i1slfa0LmLQMRzmNQmV6z/tb2SkVrfu5mCkddeHT9KIbfO3gy9xkVDCtOpIzGbNkNd7MEgnq8ZDWUUHHF2M+AIIlaqHeD40vF6kOkCgYAZIBa+m5rZBavqj6f9wRV6FomSKHBsygN2d/1dtT5+3hq/jdEnmfzqUdr9K1/8SY9y0tjmGhgCOkAtqtpwuxeu+tTDP3qb0RIZ2+8kXH46SHFcnDAiM0HKujCZ29F+vD2lPYjj3FkW48Kbz//dJrR90ARR6L0RvbJQGHJQ8EXkpwKBgCB2HsSQEVINWW2OX/4Ws6J01m2qJ3QRFM6ncx7hoFMTjjBQbNDKC44DTnCWsMs9f3BJdXNsEMVNN/vgpvT/3ZBG84/5hZL6OtIEkALQyM8zYz7fl0iBCVH2fEJlkx7PeziwI02onIgPnqF/K3tEJEECV8mcte3l8PY0a6ufWPUj";

    @Test
    void testRSA() {
        String originalString = "test";

        // 公钥加密
        String encryptedString = encryptRSA(originalString, PublicKey);
        System.out.println(encryptedString);

        // 私钥解密
        String decryptedString = decryptRSA(encryptedString, PrivateKey);
        System.out.println(decryptedString);
    }

    @Test
    void generateKeyPair() throws Exception {
        // 创建RSA密钥对生成器
        KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
        // 设置密钥长度
        keyPairGenerator.initialize(2048);
        // 生成RSA密钥对
        KeyPair keyPair = keyPairGenerator.generateKeyPair();
        // 获取公钥和私钥
        String publicKey = Base64.getEncoder().encodeToString(keyPair.getPublic().getEncoded());
        String privateKey = Base64.getEncoder().encodeToString(keyPair.getPrivate().getEncoded());
        // 打印公钥和私钥
        System.out.println("Public Key: " + publicKey);
        System.out.println("Private Key: " + privateKey);
    }

    private String encryptRSA(String originalString, String publicKey) {
        try {
            // 公钥对象
            PublicKey publicKeyObj = KeyFactory.getInstance("RSA")
                    .generatePublic(new X509EncodedKeySpec(Base64.getDecoder().decode(publicKey)));
            byte[] originalBytes = originalString.getBytes(StandardCharsets.UTF_8);
            // 加密
            Cipher cipher = Cipher.getInstance("RSA");
            cipher.init(Cipher.ENCRYPT_MODE, publicKeyObj);
            byte[] encryptedBytes = cipher.doFinal(originalBytes);
            // Base64编码
            return Base64.getEncoder().encodeToString(encryptedBytes);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private String decryptRSA(String encryptedString, String privateKey) {
        try {
            // 私钥对象
            PrivateKey privateKeyObj = KeyFactory.getInstance("RSA")
                    .generatePrivate(new PKCS8EncodedKeySpec(Base64.getDecoder().decode(privateKey)));
            // Base64解码
            byte[] encryptedBytes = Base64.getDecoder().decode(encryptedString);
            // 解密
            Cipher cipher = Cipher.getInstance("RSA");
            cipher.init(Cipher.DECRYPT_MODE, privateKeyObj);
            byte[] decryptedBytes = cipher.doFinal(encryptedBytes);
            // 明文
            return new String(decryptedBytes, StandardCharsets.UTF_8);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
