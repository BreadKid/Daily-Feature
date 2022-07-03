package com.breadykid.dailyfeature.redis.client;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

/**
 * 依赖小，速度快
 */
@Service
public class JedisService {
    @Autowired
    private JedisPool pool;

    private Jedis getJedis() {
        return pool.getResource();
    }

    private void close(Jedis jedis) {
        if (jedis!=null) {
            pool.close();
        }
    }

    public void set(String k, Object v) {
        getJedis().set(k,v.toString());
    }
}
