package com.emlynma.java.common.json;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.CollectionType;
import com.fasterxml.jackson.databind.type.MapType;

import java.util.List;
import java.util.Map;

public abstract class JsonUtils {

    private static final ObjectMapper OBJECT_MAPPER;

    static {
        OBJECT_MAPPER = new ObjectMapper();
        OBJECT_MAPPER.findAndRegisterModules();
        OBJECT_MAPPER.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    }

    public static String toJson(Object object) {
        try {
            return OBJECT_MAPPER.writeValueAsString(object);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    public static <T> T toObject(String json, Class<T> objectClass) {
        try {
            return OBJECT_MAPPER.readValue(json, objectClass);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    public static <T> T toObject(String json, TypeReference<T> typeReference) {
        try {
            return OBJECT_MAPPER.readValue(json, typeReference);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    public static <V> Map<String, V> toMap(String json, Class<V> valueClass) {
        try {
            MapType mapType = OBJECT_MAPPER.getTypeFactory().constructMapType(Map.class, String.class, valueClass);
            return OBJECT_MAPPER.readValue(json, mapType);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    public static <E> List<E> toList(String json, Class<E> elementClass) {
        try {
            CollectionType collectionType = OBJECT_MAPPER.getTypeFactory().constructCollectionType(List.class, elementClass);
            return OBJECT_MAPPER.readValue(json, collectionType);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    public static <T> T clone(Object object, Class<T> objectClass) {
        try {
            return OBJECT_MAPPER.readValue(OBJECT_MAPPER.writeValueAsBytes(object), objectClass);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
