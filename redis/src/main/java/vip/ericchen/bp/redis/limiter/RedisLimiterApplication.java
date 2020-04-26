package vip.ericchen.bp.redis.limiter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import vip.ericchen.bp.redis.limiter.impl.SimpleRateLimiter;

@SpringBootApplication
public class RedisLimiterApplication {
    @Autowired
    private SimpleRateLimiter simpleRateLimiter;

    public static void main(String[] args) {


    }

}
