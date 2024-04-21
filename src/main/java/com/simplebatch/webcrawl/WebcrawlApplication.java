package com.simplebatch.webcrawl;

import com.simplebatch.webcrawl.util.RedisApi;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.core.env.Environment;

import java.util.Arrays;
import java.util.List;

@EnableCaching
@SpringBootApplication
public class WebcrawlApplication implements CommandLineRunner {

	@Autowired
	private RedisApi myRedisApi;
	@Autowired
	private Environment env;

	public static void main(String[] args) {
		System.out.println( "System environment variable" + System.getenv());
		SpringApplication.run(WebcrawlApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		// Example usage of MyService for storing and retrieving data
		String redisHost = env.getProperty("spring.redis.host");

		System.out.println("Active profile"+ env.getActiveProfiles());

		System.out.println("Using Redis host from Environment: " + redisHost);

		String key = "testKey";
		String[] value = {"value1", "value2", "value3"};

		// Store data
		myRedisApi.putArray(key, value);
		System.out.println("Stored data with key: " + key);

		// Retrieve data
		List<String> retrievedList = List.of(myRedisApi.getArray(key));
		System.out.println("Retrieved data: " + retrievedList);
	}
}
