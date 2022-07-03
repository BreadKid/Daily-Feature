package com.breadykid.dailyfeature.redis.client;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

/**
 * @description: Redis Template使用
 * @author: Joyce Liu
 */
@Service
public class RedisTemplateService {
    @Autowired
    private StringRedisTemplate redisTemplate;

    public void set(String k, Object v) {
        redisTemplate.opsForValue().set(k, v.toString(),100, TimeUnit.MINUTES);
    }

    public String get(String k) {
        return redisTemplate.opsForValue().get(k);
    }
}
