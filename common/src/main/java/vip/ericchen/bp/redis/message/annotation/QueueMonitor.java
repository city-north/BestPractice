package vip.ericchen.bp.redis.message.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * <p>
 * Indicates a Monitor which monitor a specified Queue
 * </p>
 *
 * @author ericchen.vip@foxmail.com 2020/04/22 19:19
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface QueueMonitor {

    /**
     * the name of the queue
     */
    String queue() default "";
}
