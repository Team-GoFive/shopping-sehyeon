package com.kt.config;

import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.springframework.boot.autoconfigure.data.redis.RedisProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import lombok.RequiredArgsConstructor;

@Configuration
@RequiredArgsConstructor
public class RedisConfiguration {

	private final RedisProperties redisProperties;

	@Bean
	public RedissonClient redisClient() {
		Config config = new Config();
		String redisUrl = String.format("redis://%s:%d", redisProperties.getHost(), redisProperties.getPort());
		config.useSingleServer().setAddress(redisUrl);
		return Redisson.create(config);
	}
}
