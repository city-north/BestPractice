package vip.ericchen.bp.redis.limiter.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import vip.ericchen.bp.redis.limiter.IRateLimiter;

import java.util.List;

/**
 * <p>
 * 滑动窗口简单实现限流
 * </p>
 *
 * @author ericchen.vip@foxmail.com 2020/04/18 21:37
 */
@Component("simpleRateLimiter")
public class SimpleRateLimiter implements IRateLimiter {

    @Autowired
    private RedisTemplate<String, String> redisTemplate;


    @Override
    public boolean isActionAllowed(String userId, String actionKey, int period, float maxCount) {
        String key = String.format("hist:%s:%s", userId, actionKey);
        long nowTs = System.currentTimeMillis();
        List answer = redisTemplate.executePipelined((RedisCallback<List>) connection -> {
            connection.multi();
            byte[] keyBytes = key.getBytes();
            connection.zAdd(keyBytes, nowTs, ("" + nowTs).getBytes()); //score 是当前时间 ,value没有特别意义,可以随便设置
            connection.zRemRangeByScore(keyBytes, 0, nowTs - period * 1000);//清除 0-60 秒之前的数据
            connection.zCard(keyBytes);//判断剩余的个数]
            connection.expire(keyBytes, period + 1L);//失效
            return connection.exec();
        });
        if (CollectionUtils.isEmpty(answer)) {
            return false;
        }
        List o = (List) answer.get(0);
        if (o != null && o.size() > 3) {
            Long o1 = (Long) o.get(2);
            return o1 <= maxCount;
        }
        return false;
    }
}
