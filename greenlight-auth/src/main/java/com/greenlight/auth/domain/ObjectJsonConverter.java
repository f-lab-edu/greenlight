package com.greenlight.auth.domain;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.greenlight.auth.exception.ConvertToObjectException;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ObjectJsonConverter {
    private ObjectJsonConverter() { throw new IllegalStateException("Utility class"); }

    private static ObjectMapper objectJsonMapper = new ObjectMapper();

    public static <T> T convertToObject(String json, Class<T> clazz) {
        try {
            return (T) objectJsonMapper.readValue(json, clazz);
        } catch (JsonProcessingException e) {
            log.error(e.getMessage(), e);
            throw new ConvertToObjectException("ObjectJsonConverter > convertToObject");
        }
    }

    public static String convertToJson(Object clazz) {
        try {
            return objectJsonMapper.setVisibility(PropertyAccessor.FIELD, JsonAutoDetect.Visibility.ANY)
                    .writeValueAsString(clazz);
        } catch (JsonProcessingException e) {
            log.error(e.getMessage(), e);
            throw new ConvertToObjectException("ObjectJsonConverter > convertToJson");
        }
    }
}
