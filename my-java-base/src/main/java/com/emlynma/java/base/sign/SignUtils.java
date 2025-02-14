package com.emlynma.java.base.sign;

import com.emlynma.java.base.json.JsonUtils;

import java.nio.charset.StandardCharsets;
import java.security.KeyFactory;
import java.security.Signature;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.*;
import java.util.stream.Collectors;

public abstract class SignUtils {

    private static final String SIGN_FIELD = "sign";

    public static String sign(Object object, String privateKey) {
        return sign(buildSignString(object), privateKey);
    }

    public static boolean verify(Object object, String sign, String publicKey) {
        return verify(buildSignString(object), sign, publicKey);
    }

    private static String sign(String content, String privateKey) {
        try {
            Signature signature = Signature.getInstance("SHA256withRSA");
            signature.initSign(KeyFactory.getInstance("RSA").generatePrivate(new PKCS8EncodedKeySpec(Base64.getDecoder().decode(privateKey))));
            signature.update(content.getBytes(StandardCharsets.UTF_8));
            return Base64.getEncoder().encodeToString(signature.sign());
        } catch (Exception e) {
            throw new RuntimeException("sign error", e);
        }
    }

    private static boolean verify(String content, String sign, String publicKey) {
        try {
            Signature signature = Signature.getInstance("SHA256withRSA");
            signature.initVerify(KeyFactory.getInstance("RSA").generatePublic(new X509EncodedKeySpec(Base64.getDecoder().decode(publicKey))));
            signature.update(content.getBytes(StandardCharsets.UTF_8));
            return signature.verify(Base64.getDecoder().decode(sign));
        } catch (Exception e) {
            throw new RuntimeException("verify error", e);
        }
    }

    private static String buildSignString(Object object) {
        if (Objects.isNull(object))
            return "";
        if (isPrimitiveType(object)) {
            return String.valueOf(object);
        }
        Map<String, Object> fieldMap = JsonUtils.toMap(JsonUtils.toJson(object), Object.class);
        return fieldMap.entrySet().stream()
                .filter(entry -> Objects.nonNull(entry.getValue()))
                .filter(entry -> isPrimitiveType(entry.getValue()))
                .filter(entry -> !String.valueOf(entry.getValue()).isEmpty())
                .filter(entry -> !entry.getKey().equals(SIGN_FIELD))
                .sorted(Map.Entry.comparingByKey())
                .map(entry -> entry.getKey() + "=" + entry.getValue())
                .collect(Collectors.joining("&"));
    }

    private static boolean isPrimitiveType(Object object) {
        return object instanceof String || object instanceof Number || object instanceof Boolean;
    }

}
