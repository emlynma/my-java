package com.emlyn.ma.base.security;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.Signature;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

/**
 * 数字签名算法 RSA
 */
public class SignatureTest {

    private static final String MyPublicKey = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEA9KA463kmFG/TGr0fmQgZNNJ5rnbEld8dgoRJX3lywK4rqX6q9lTJrm16Hc6OQf9XTy6TawD2FqwOUxajn+Tq99pGB1W+as1OHVoNJ5CtstSZ+cKQyPELZ5qW5SG9I3N2wzE/YCpIqMsVizFqTQnoe6PZaLPTguiShcSxDC7tJhhEPgvaV9olwdEox23ht7rHh7oha698XDNnPin+4E7OO6sJ7m0sZXZ7jJIs6dz5jzxOIql7A3RS84y1r6g5JO4jTyFDP5pTiCHT7uKTYpGJ41g23fYDbLdMgV4CVC8GN17QfD/ojXnOTY0n7CxItFpbsv/bhSjKv/eP6AK3KR4GeQIDAQAB";
    private static final String MyPrivateKey = "MIIEuwIBADANBgkqhkiG9w0BAQEFAASCBKUwggShAgEAAoIBAQD0oDjreSYUb9MavR+ZCBk00nmudsSV3x2ChElfeXLAriupfqr2VMmubXodzo5B/1dPLpNrAPYWrA5TFqOf5Or32kYHVb5qzU4dWg0nkK2y1Jn5wpDI8QtnmpblIb0jc3bDMT9gKkioyxWLMWpNCeh7o9los9OC6JKFxLEMLu0mGEQ+C9pX2iXB0SjHbeG3useHuiFrr3xcM2c+Kf7gTs47qwnubSxldnuMkizp3PmPPE4iqXsDdFLzjLWvqDkk7iNPIUM/mlOIIdPu4pNikYnjWDbd9gNst0yBXgJULwY3XtB8P+iNec5NjSfsLEi0Wluy/9uFKMq/94/oArcpHgZ5AgMBAAECgf84Jk4kCF2PMwuc0T+hD/tj1z77PJhvsodp/g8C8RhST/H4k70trUPzHm53HzixijownCdz+F3iqokbvAL3rSiU5RCqcoZGFuoLF+R5xS3iNzafb3cIPnVZ7jfZd0lk5CGuEYwtOVlM/7qSfu2Q9l7EBxdicRAxhWEoADZZhpDT1rw4Qrz+TjmCdOpJSMU0wSvgfNUFACLZQ4QoVoECYJ0gtmVaf/WtababLP7fLWSZ+5GGqxwYxOiOOxfRyIi8DNttfffnKn9J9nlMAUz8/MxsCDPN1soHdTbgTWpR7mwyVbhnzBC9iWegtJXiu1Dwqc29pxvy8ROSdJkXa9Mc7scCgYEA+Ik3jAGwycFUQIeult8OfjYbDL1OKNrGZP7g/I58h3aEHpMbtPpGJ94fUGs+GLjIK6ZXJkAllZPrQFP66s/UaVTN6+2lwjpSYUii+23xBemXWqKyZ1q8HwrvIv7TiRMi2XjrT0rZFgKYjRO8TPMljj8Y5pROdn+EAqAgFG6ptmsCgYEA+/jxknvsmCvBLK4wFQoJmrKKT3HShz6mCXVlpNPb9u9qG63DVhv9gUOgOKjOMnAaz1GWgNeJtURLrircN9fnApECyNBR7HqcQtTotAcnFijOGEUhqlernokA0xWPM5TV8kHRH+WOScIV/7Xe53XcDxAG+FMCGFFToK5WyOfcx6sCgYEAu4GqqrJL0qtNCTJUgfbfVFE1rzLTWPdVXrT8wQ4u3XSI0RhjevKnI0TtkxqVysj+HBBvB6i1slfa0LmLQMRzmNQmV6z/tb2SkVrfu5mCkddeHT9KIbfO3gy9xkVDCtOpIzGbNkNd7MEgnq8ZDWUUHHF2M+AIIlaqHeD40vF6kOkCgYAZIBa+m5rZBavqj6f9wRV6FomSKHBsygN2d/1dtT5+3hq/jdEnmfzqUdr9K1/8SY9y0tjmGhgCOkAtqtpwuxeu+tTDP3qb0RIZ2+8kXH46SHFcnDAiM0HKujCZ29F+vD2lPYjj3FkW48Kbz//dJrR90ARR6L0RvbJQGHJQ8EXkpwKBgCB2HsSQEVINWW2OX/4Ws6J01m2qJ3QRFM6ncx7hoFMTjjBQbNDKC44DTnCWsMs9f3BJdXNsEMVNN/vgpvT/3ZBG84/5hZL6OtIEkALQyM8zYz7fl0iBCVH2fEJlkx7PeziwI02onIgPnqF/K3tEJEECV8mcte3l8PY0a6ufWPUj";

    @Test
    void testSignWithRSA() {
        try {
            // 待签名的数据
            String data = "hello world";

            // 生成签名
            String sign = sign(data, MyPrivateKey);

            // 验证签名
            Assertions.assertTrue(verity(sign, data, MyPublicKey));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

    private String sign(String originalString, String privateKeyString) {
        try {
            // 创建签名对象
            Signature signature = Signature.getInstance("SHA256withRSA");
            // 私钥对象
            PrivateKey privateKey = KeyFactory.getInstance("RSA")
                    .generatePrivate(new PKCS8EncodedKeySpec(Base64.getDecoder().decode(privateKeyString)));
            // 使用私钥进行签名
            signature.initSign(privateKey);
            signature.update(originalString.getBytes());
            byte[] sign = signature.sign();
            // 将签名结果转换为 Base64 编码的字符串
            return Base64.getEncoder().encodeToString(sign);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private Boolean verity(String sign, String originalString, String publicKeyString) {
        try {
            // 创建签名对象
            Signature signature = Signature.getInstance("SHA256withRSA");
            // 公钥对象
            java.security.PublicKey publicKey = KeyFactory.getInstance("RSA")
                    .generatePublic(new X509EncodedKeySpec(Base64.getDecoder().decode(publicKeyString)));
            // 使用公钥进行验证
            signature.initVerify(publicKey);
            signature.update(originalString.getBytes());
            return signature.verify(Base64.getDecoder().decode(sign));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

}
