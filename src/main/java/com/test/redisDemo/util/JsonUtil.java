package com.test.redisDemo.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.validation.ObjectError;

public class JsonUtil {

    private static final ObjectMapper MAPPER = new ObjectMapper();

    /**
     * 把对象转化为字符串
     *
     * @param data
     * @return
     */
    public static String objectToJson(Object data) {
        try {
            return MAPPER.writeValueAsString(data);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    /**
     * Json字符串转对象
     *
     * @param jsonData
     * @param beanType
     * @param <T>
     * @return
     */
    public static <T> T jsonToPojo(String jsonData, Class<T> beanType) {
        try {
            T t = MAPPER.readValue(jsonData, beanType);
            return t;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
