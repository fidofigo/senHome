package com.senhome.shell.common.zlCache.factory.redis;

import com.senhome.shell.common.zlCache.factory.CacheClientIF;
import com.senhome.shell.common.zlCache.factory.redis.operations.*;

import java.util.Set;

/**
 * @author xier
 * @date 2018/10/7 10:41
 */
public interface RedisClientIF extends CacheClientIF, HashOperations, ListOperations, SetOperations, ZSetOperations, PubSubOperations
{
    /**
     * 注意如果使用阿里云集群版，因为不支持eval，这个功能无法使用哦
     * @param prefix
     * @return
     */
    boolean cleanKeysByPrefix(String prefix);

    /**
     * 设置过期时间
     * @param key
     * @param second 秒
     * @return
     */
    Boolean expire(String key, int second);

    /**
     * 返回符合给定模式 pattern 的 key
     * @see "http://redisdoc.com/key/keys.html"
     * @param pattern
     * @return set or null
     */
    Set<String> keys(String pattern);

    /**
     * 原子性操作，设置新值，返回旧值
     *
     * @param key
     * @param value
     * @return
     */
    <T> T getSet(String key, String value);

    /**
     * 返回符合给定模式 pattern 的 key
     *
     * 该命令不会影响系统性能
     * 但接口耗时很长，不建议同步调用
     *
     * @see "http://redisdoc.com/key/keys.html"
     * @param pattern
     * @return set or null
     */
    Set<String> scan(String pattern);

    String status();

    Boolean exists(String key);
}
