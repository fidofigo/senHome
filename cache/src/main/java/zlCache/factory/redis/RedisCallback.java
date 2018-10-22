package zlCache.factory.redis;

import redis.clients.jedis.Jedis;

/**
 * @author xier
 * @date 2018/10/7 10:52
 */
public interface RedisCallback<T>
{
    T doInRedis(Jedis jedis);
}
