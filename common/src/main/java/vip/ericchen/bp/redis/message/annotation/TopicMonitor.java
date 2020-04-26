package vip.ericchen.bp.redis.message.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * <p>
 * indicates a class that monitors specific topics
 * </p>
 *
 * @author ericchen.vip@foxmail.com 2020/04/22 19:21
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.CLASS)
public @interface TopicMonitor {

    /**
     * Indicates the channels to monitor
     */
    String[] channel() default {};
}
