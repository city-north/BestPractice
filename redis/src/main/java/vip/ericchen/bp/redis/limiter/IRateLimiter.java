package vip.ericchen.bp.redis.limiter;

/**
 * <p>
 * 使用 Redis 做用户行为 限流
 * </p>
 *
 * @author EricChen.vip@foxmail.com
 */
@FunctionalInterface
public interface IRateLimiter {


    /**
     * 是否允许
     *
     * @param userId    用户 ID
     * @param actionKey 操作标识
     * @param period    周期
     * @param maxCount  最大
     * @return 是否允许行为
     */
    boolean isActionAllowed(String userId, String actionKey, int period, float maxCount);


}
