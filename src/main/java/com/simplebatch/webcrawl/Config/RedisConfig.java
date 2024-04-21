package com.simplebatch.webcrawl.Config;

import com.simplebatch.webcrawl.util.RedisApi;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.data.redis.RedisProperties;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import io.lettuce.core.RedisURI;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import reactor.core.publisher.Mono;
import io.lettuce.core.RedisClient;
import org.springframework.data.redis.connection.ReactiveRedisConnectionFactory;

import java.util.List;


@Configuration
public class RedisConfig {
    private final String host;
    private final int port;

    private final String password;

    @Autowired
    public RedisConfig(@Value("${spring.redis.host}") String host,
                       @Value("${spring.redis.port}") int port,
                       @Value("${spring.redis.password}") String password) {
        this.host = host;
        this.port = port;
        this.password = password;
    }
    @Autowired
    private Environment env;

    @Bean
    public RedisConnectionFactory redisConnectionFactory() {
        LettuceConnectionFactory lettuceConnectionFactory = new LettuceConnectionFactory(host, port);
        lettuceConnectionFactory.setPassword(password);
        return lettuceConnectionFactory;
    }

    @Bean
    public RedisTemplate<String, List<String>> redisTemplate(RedisConnectionFactory redisConnectionFactory) {
        String redisHost = env.getProperty("spring.redis.host");  // Optional: For verification
        String redisPort = env.getProperty("spring.redis.port");
        System.out.println("Using Redis host from Environment (for verification): \"spring.redis.host\" " + redisHost);
        System.out.println("Using Redis host from Environment (for verification): \"spring.redis.port\" " + redisPort);


        RedisTemplate<String, List<String>> redisTemplate = new RedisTemplate<>();
        redisTemplate.setConnectionFactory(redisConnectionFactory);
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        redisTemplate.setValueSerializer(new StringRedisSerializer()); // Add value serializer for String


        // Add more configuration if needed
        return redisTemplate;
    }
}
