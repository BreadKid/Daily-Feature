package com.breadykid.dailyfeature;

import com.breadykid.dailyfeature.redis.client.RedisTemplateService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @description:
 * @author: Joyce Liu
 */
public class RedisTest extends DailyFeatureApplicationTests {

    @Autowired
    private RedisTemplateService redisTemplateService;
    @Test
    public void setTest() {
        String k = "aaa";
        String v = "123";
        redisTemplateService.set(k, v);
        String result = redisTemplateService.get(k);
        assert v.equals(result);
    }
}
