package vip.ericchen.bp.redis.limiter.impl;

import vip.ericchen.bp.redis.limiter.IRateLimiter;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * <p>
 * 漏斗限流
 * </p>
 *
 * @author ericchen.vip@foxmail.com 2020/04/18 23:25
 */
public class FunnelRateLimiter implements IRateLimiter {
    private Map<String, Funnel> funnelMap = new ConcurrentHashMap<>();

    @Override
    public boolean isActionAllowed(String userId, String actionKey, int capacity, float leakingRate) {
        String key = String.format("%s,%s", userId, actionKey);
        Funnel funnel = funnelMap.get(actionKey);
        if (funnel == null) {
            funnel = new Funnel(capacity, leakingRate);
            funnelMap.put(key, funnel);
        }
        return funnel.watering(1);
    }

    static class Funnel {
        int capacity; //漏斗容量
        float leakingRate; // 漏嘴流水速率
        int leftQuota;//漏斗剩余空间
        long leakingTs;//上一次漏水时间

        public Funnel(int capacity, float leakingRate) {
            this.capacity = capacity;
            this.leakingRate = leakingRate;
            this.leftQuota = capacity;
            this.leakingTs = System.currentTimeMillis();
        }

        void makeSpace() {
            long nowTs = System.currentTimeMillis();
            long deltaTs = nowTs - leakingTs;
            int deltaQuota = (int) (deltaTs * leakingRate);
            //间隔时间太长
            if (deltaQuota < 0) {
                this.leftQuota = capacity;
                this.leakingTs = nowTs;
                return;
            }
            if (deltaQuota < 1) {
                return;
            }
            this.leftQuota += deltaQuota;
            this.leakingTs = nowTs;
            if (this.leftQuota > this.capacity) {
                this.leftQuota = this.capacity;
            }
        }

        boolean watering(int quota) {
            makeSpace();
            if (this.leftQuota >= quota) {
                this.leftQuota -= quota;
                return true;
            }
            return false;
        }

    }


}
