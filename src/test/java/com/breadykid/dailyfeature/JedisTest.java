package com.breadykid.dailyfeature;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import redis.clients.jedis.JedisPool;

/**
 * @description:
 * @author: Joyce Liu
 */
public class JedisTest extends DailyFeatureApplicationTests{

    @Autowired
    private JedisPool pool;

    @Test
    public void connectTest() {
        System.out.println(pool);
    }
}
