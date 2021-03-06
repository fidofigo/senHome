package ywCache.factory;

import ywCache.config.RedisCacheConfig;
import ywCache.localcache.LocalCacheClientImpl;
import ywCache.redis.RedisCacheClientImpl;
import ywCache.serializer.Serializer;

import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author xier
 * @date 2018/10/7 10:32
 */
public class YWCacheFactory
{
    /**
     * redis map
     */
    private static ConcurrentHashMap<RedisCacheConfig, CacheClientIF> redisClientMap = new ConcurrentHashMap<>();

    /**
     * localCache map
     */
    private volatile static CacheClientIF localCacheClient;

    /**
     * redis 缓存客户端
     * @param redisCacheConfig
     * @return
     */
    public static synchronized CacheClientIF getRedisClient(RedisCacheConfig redisCacheConfig)
    {
        return getRedisClient(redisCacheConfig, null, null);
    }

    public static synchronized CacheClientIF getRedisClient(RedisCacheConfig redisCacheConfig, Serializer keySerializer, Serializer valueSerializer) {
        CacheClientIF cacheClient = redisClientMap.get(redisCacheConfig);
        if (null == cacheClient) {
            cacheClient = new RedisCacheClientImpl(redisCacheConfig, keySerializer, valueSerializer);
            redisClientMap.put(redisCacheConfig, cacheClient);
        }
        return cacheClient;
    }

    /***
     * 本地缓存客户端
     *
     * @return CacheClientIF
     */
    public static synchronized CacheClientIF getLocalCacheClient() {
        if (Objects.isNull(localCacheClient)) {
            localCacheClient = new LocalCacheClientImpl();
            return localCacheClient;
        }
        return localCacheClient;
    }
}
