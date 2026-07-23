package com.project.example.ai.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Component;

@Component
public class JsonUtil {

    private final ObjectMapper mapper = new ObjectMapper();

    public <T> T fromJson(String json, Class<T> clazz) {

        try {
            return mapper.readValue(json, clazz);
        } catch (Exception e) {
            throw new RuntimeException("Invalid JSON received from AI", e);
        }

    }

}