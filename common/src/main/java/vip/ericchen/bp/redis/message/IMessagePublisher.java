package vip.ericchen.bp.redis.message;

/**
 * <p>
 * 消息发布者
 * </p>
 *
 * @author ericchen.vip@foxmail.com 2020/04/22 19:28
 */
public interface IMessagePublisher {

    /**
     * 发布一个消息到频道中
     *
     * @param channel 频道
     * @param message 消息
     */
    void publish(String channel, Object message);

    /**
     * 发布一个消息到队列中
     *
     * @param queueName 队列名称
     * @param message   消息
     */
    void message(String queueName, Object message);

}
