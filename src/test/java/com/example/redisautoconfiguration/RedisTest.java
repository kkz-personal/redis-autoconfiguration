package com.example.redisautoconfiguration;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class RedisTest {
    /**
     * 默认 main
     */
    @Autowired
    StringRedisTemplate mainStringRedisTemplate;

    /**
     * 指定template实例
     */
    @Autowired
    @Qualifier("lesserStringRedisTemplate")
    StringRedisTemplate lesserStringRedisTemplate;

    @Test
    public void test(){
        System.out.println("begin");
        mainStringRedisTemplate.opsForValue().set("test","this is 6379");
        lesserStringRedisTemplate.opsForValue().set("test","this is 6333");

    }
}
