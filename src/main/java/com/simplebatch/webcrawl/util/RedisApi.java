package com.simplebatch.webcrawl.util;

import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.data.redis.RedisProperties;
import org.springframework.core.env.Environment;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@Slf4j
public class RedisApi {
    private final RedisTemplate<String, String> redisTemplate;

    @Autowired
    public RedisApi(RedisTemplate<String, String> redisTemplate) {
        this.redisTemplate = redisTemplate;

        System.out.println( "Connection Factory " + redisTemplate.getConnectionFactory().toString());
        System.out.println("Using Redis host from RedisTemplate: " + redisTemplate);
    }

    public void putArray(String key, String[] values) {
        redisTemplate.opsForList().rightPushAll(key, values);
    }

    public String[] getArray(String key) {
        return redisTemplate.opsForList().range(key, 0, -1).toArray(new String[0]);
    }
}
