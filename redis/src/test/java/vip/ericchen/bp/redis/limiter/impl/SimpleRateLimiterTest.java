package vip.ericchen.bp.redis.limiter.impl;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.test.context.junit4.SpringRunner;
import vip.ericchen.bp.redis.limiter.IRateLimiter;

/**
 * <p>
 * description
 * </p>
 *
 * @author ericchen.vip@foxmail.com 2020/04/18 21:59
 */
@SpringBootTest
@Slf4j
public class SimpleRateLimiterTest {


    @Autowired
    @Qualifier("simpleRateLimiter")
    IRateLimiter simpleRateLimiter;

    @Autowired
    private StringRedisTemplate redisTemplate;


    @Test
    public void testSimpleLimier() {
        for (int i = 0; i < 20; i++) {
            //每 60 秒 5 次
            log.debug("test time ={} , result = {}", i, simpleRateLimiter.isActionAllowed("eric", "replay", 60, 5));
        }
    }

    @Test
    public void insertData() {
        for (int i = 0; i < 100000; i++) {
            redisTemplate.opsForValue().set("key" + i, i + "");
        }
    }
}