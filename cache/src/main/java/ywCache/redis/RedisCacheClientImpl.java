package ywCache.redis;

import ywCache.config.RedisCacheConfig;
import ywCache.constant.Constants;
import ywCache.exception.CacheException;
import ywCache.factory.redis.AbstractRedisClient;
import ywCache.factory.redis.RedisCallback;
import ywCache.serializer.JdkSerializer;
import ywCache.serializer.Serializer;
import ywCache.serializer.StringSerializer;
import ywCache.support.holder.SyncLockHolder;
import ywCache.util.ObjectUtil;
import ywCache.util.SerializationUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.DisposableBean;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;
import redis.clients.util.SafeEncoder;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.locks.ReentrantLock;
import java.util.function.Supplier;

/**
 * @author xier
 * @date 2018/10/7 10:34
 */
public class RedisCacheClientImpl extends AbstractRedisClient implements DisposableBean
{
    private static final Logger log = LoggerFactory.getLogger(RedisCacheClientImpl.class);

    /**
     * 配置
     */
    private RedisCacheConfig redisCacheConfig;

    private Serializer keySerializer = new StringSerializer();

    private Serializer valueSerializer = new JdkSerializer();

    /**
     * 连接池
     */
    private JedisPool jedisPool;

    public RedisCacheClientImpl(RedisCacheConfig redisCacheConfig) {
        this.redisCacheConfig = redisCacheConfig;
        init();
    }

    public RedisCacheClientImpl(RedisCacheConfig redisCacheConfig, Serializer keySerializer, Serializer valueSerializer) {
        this(redisCacheConfig);
        if (null != keySerializer) {
            this.keySerializer = keySerializer;
        }
        if (null != valueSerializer) {
            this.valueSerializer = valueSerializer;
        }
    }

    /**
     * 初始化方法
     */
    private void init()
    {
        JedisPoolConfig jpconfig = new JedisPoolConfig();
        jpconfig.setMaxTotal(redisCacheConfig.getMaxTotal());
        jpconfig.setMaxIdle(redisCacheConfig.getMaxIdle());
        jpconfig.setMaxWaitMillis(redisCacheConfig.getMaxWait());
        jpconfig.setMinIdle(1);
        jpconfig.setTestOnBorrow(false);
        jpconfig.setTestOnReturn(false);
        String password = ObjectUtil.isNullOrEmptyStr(redisCacheConfig.getPassword()) ? null : redisCacheConfig.getPassword();
        jedisPool = new JedisPool(jpconfig, redisCacheConfig.getHost(), redisCacheConfig.getPort(), redisCacheConfig.getTimeout(), password);
    }

    @Override
    public byte[] serializeKey(Object key) {
        return SerializationUtil.serialize(keySerializer, key);
    }

    @Override
    public String deserializeKey(byte[] byteKey) {
        return (String) SerializationUtil.deserialize(keySerializer, byteKey);
    }

    @Override
    public byte[] serializeValue(Object value) {
        return SerializationUtil.serialize(valueSerializer, value);
    }

    @Override
    public Object deserializeValue(byte[] byteValue) {
        return SerializationUtil.deserialize(valueSerializer, byteValue);
    }

    @Override
    public void put(String key, Object value, int second) {
        // check key
        if (ObjectUtil.isNullOrEmptyStr(key)) {
            throw new CacheException("key Can not be empty or null");
        }

        // check value
        if (ObjectUtil.isNull(value)) {
            return;
        }

        // redis 实例对象
        try (Jedis jedis = jedisPool.getResource()) {
            jedis.setex(getFullKeyWithSerialize(key), second, serializeValue(value));
        } catch (Exception e) {
            throw new CacheException(e);
        }
    }

    @Override
    public void putBytes(String key, byte[] bytes, int second) {
        // check key
        if (ObjectUtil.isNullOrEmptyStr(key)) {
            throw new CacheException("key Can not be empty or null");
        }

        // check value
        if (ObjectUtil.isNull(bytes)) {
            return;
        }

        // redis 实例对象
        try (Jedis jedis = jedisPool.getResource()) {
            jedis.setex(getFullKeyWithSerialize(key), second, bytes);
        } catch (Exception e) {
            throw new CacheException(e);
        }
    }

    @SuppressWarnings("unchecked")
    @Override
    public <T> T get(String key) {
        // check key
        if (ObjectUtil.isNullOrEmptyStr(key)) {
            throw new CacheException("key Can not be empty or null");
        }

        // redis 实例对象
        try (Jedis jedis = jedisPool.getResource()) {
            byte[] result = jedis.get(getFullKeyWithSerialize(key));
            if (ObjectUtil.isNull(result)) {
                return null;
            }
            return (T)deserializeValue(result);
        } catch (Exception e) {
            throw new CacheException(e);
        }
    }

    @Override
    public byte[] getRawBytes(String key) {
        // check key
        if (ObjectUtil.isNullOrEmptyStr(key)) {
            throw new CacheException("key Can not be empty or null");
        }

        // redis 实例对象
        try (Jedis jedis = jedisPool.getResource()) {
            byte[] result = jedis.get(getFullKeyWithSerialize(key));
            return result;
        } catch (Exception e) {
            throw new CacheException(e);
        }
    }

    @Override
    public boolean delete(String key) {
        // 判断是否为空
        if (ObjectUtil.isNullOrEmptyStr(key)) {
            throw new CacheException("key Can not be empty or null");
        }

        // redis 实例对象
        try (Jedis jedis = jedisPool.getResource()) {
            Long result = jedis.del(getFullKeyWithSerialize(key));
            return !(result == null || result == 0);
        } catch (Exception e) {
            throw new CacheException(e);
        }

    }

    @Override
    public Map<String, Object> getAllByKeys(List<String> keys) {
        if (ObjectUtil.isNullOrEmpty(keys)) {
            throw new CacheException("keys Can not be null or length be 0");
        }

        // redis 实例对象
        Map<String, Object> map = new LinkedHashMap<>();
        try (Jedis jedis = jedisPool.getResource()) {
            byte[][] bkeys = new byte[keys.size()][];
            for (int i = 0; i < keys.size(); i++) {
                bkeys[i] = serializeKey(getFullKey(keys.get(i)));
            }
            List<byte[]> list = jedis.mget(bkeys);
            int i = 0;
            // redis 默认按传进来的key顺序返回查询集合，不存在的 返回null。
            for (byte[] bs : list) {
                // 判断是否为null，为null 直接放入map。跳出循环。
                if (ObjectUtil.isNull(bs)) {
                    map.put(keys.get(i), null);
                    i++;
                    continue;
                }
                // 不为null 执行下面的代码
                Object object = deserializeValue(bs);
                map.put(keys.get(i), object);
                i++;
            }
            return map;
        } catch (Exception e) {
            throw new CacheException(e);
        }
    }

    @Override
    public boolean delete(List<String> keys) {
        if (ObjectUtil.isNullOrEmpty(keys)) {
            throw new CacheException("keys Can not be null or length be 0");
        }

        // redis 实例对象
        try (Jedis jedis = jedisPool.getResource()) {
            byte[][] bkeys = new byte[keys.size()][];
            for (int i = 0; i < keys.size(); i++)
            {
                bkeys[i] = serializeKey(getFullKey(keys.get(i)));
            }
            return jedis.del(bkeys) > 0;
        } catch (Exception e) {
            throw new CacheException(e);
        }
    }

    @Override
    public long incr(String key, long delta) {
        if (ObjectUtil.isNullOrEmptyStr(key)) {
            throw new CacheException("key Can not be empty or null");
        }
        // delta 参数必须大于0
        if (Constants.NUMBER_ZERO >= delta) {
            throw new CacheException("delta Can not be 0 or negative");
        }

        // redis 实例对象
        try (Jedis jedis = jedisPool.getResource()) {
            return jedis.incrBy(getFullKeyWithSerialize(key), delta);
        } catch (Exception e) {
            throw new CacheException(e);
        }
    }

    @Override
    public long decr(String key, int decrement) {
        if (ObjectUtil.isNullOrEmptyStr(key)) {
            throw new CacheException("key Can not be empty or null");
        }
        if (Constants.NUMBER_ZERO >= decrement) {
            throw new CacheException("decrement Can not be 0 or negative");
        }

        // redis 实例对象
        try (Jedis jedis = jedisPool.getResource()) {
            return jedis.decrBy(getFullKeyWithSerialize(key), decrement);
        } catch (Exception e) {
            throw new CacheException(e);
        }
    }

    @Override
    public Long getLong(String key) {
        // 判断是否为空
        if (ObjectUtil.isNullOrEmptyStr(key)) {
            throw new CacheException("key Can not be empty or null");
        }

        try (Jedis jedis = jedisPool.getResource()) {
            byte[] result = jedis.get(getFullKeyWithSerialize(key));
            if (ObjectUtil.isNull(result)) {
                return null;
            }
            return Long.valueOf(SafeEncoder.encode(result));
        } catch (Exception e) {
            throw new CacheException(e);
        }
    }

    @Override
    public <T> T getOrCreate(String key, Supplier<T> supplier, int expireTime) {
        T result = get(key);
        if (Objects.nonNull(result)) {
            return result;
        }
        T supplierResult = supplier.get();
        put(key, supplierResult, expireTime);
        return supplierResult;
    }

    @Override
    public <T> T getOrCreate(String key, Supplier<T> supplier) {
        T result = get(key);
        if (Objects.nonNull(result)) {
            return result;
        }
        T supplierResult = supplier.get();
        put(key, supplierResult, Constants.ONE_MINUTE);
        return supplierResult;
    }

    @Override
    public <T> T getOrCreateWithSyncLock(String key, Supplier<T> supplier, int expireTime) {
        T result = get(key);
        if (Objects.nonNull(result)) {
            return result;
        }
        ReentrantLock lock = null;
        try {
            try {
                lock = SyncLockHolder.getLockByKey(getFullKey(key));
                if (null != lock) {
                    lock.lock();
                }
            } catch (Throwable e) {
                log.error(e.getMessage(), e);
            }
            result = get(key);
            if (null == result) {
                result = supplier.get();
                put(key, result, expireTime);
            }
        } finally {
            if (null != lock) {
                try {
                    lock.unlock();
                } catch (Throwable e) {
                    log.error(e.getMessage(), e);
                }
            }
        }

        return result;
    }

    @Override
    public String getPrefix()
    {
        return redisCacheConfig.getPrefix();
    }

    @Override
    public void destroy() throws Exception {
        if (null != jedisPool) {
            try {
                jedisPool.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    protected <T> T execute(RedisCallback<T> action) {
        try (Jedis jedis = jedisPool.getResource()) {
            return action.doInRedis(jedis);
        } catch (Throwable e) {
            throw new CacheException(e);
        }
    }

    @Override
    protected <T> T executeInUseRawJedis(RedisCallback<T> action) {
        Jedis jedis = null;
        try {
            jedis = new Jedis(redisCacheConfig.getHost(), redisCacheConfig.getPort(), redisCacheConfig.getTimeout(), redisCacheConfig.getTimeout());
            if (!ObjectUtil.isNullOrEmptyStr(redisCacheConfig.getPassword())) {
                jedis.auth(redisCacheConfig.getPassword());
            }
            jedis.connect();
            return action.doInRedis(jedis);
        } catch (Throwable e) {
            throw new CacheException(e);
        } finally {
            if (null != jedis) {
                try {
                    jedis.close();
                } catch (Throwable e) {
                }
            }
        }
    }

    @Override
    public boolean acquireLock(String key, int ttl) {
        if (ObjectUtil.isNullOrEmptyStr(key)) {
            return false;
        }

        try (Jedis jedis = jedisPool.getResource()) {
            String result = jedis.set(getFullKeyWithSerialize(key), serializeValue(1), stringSerializer.serialize("NX"), stringSerializer.serialize("EX"), Long.valueOf(ttl));
            return "OK".equalsIgnoreCase(result);
        } catch (Throwable e) {
            return false;
        }
    }

    @Override
    public boolean releaseLock(String key) {
        try {
            return delete(key);
        } catch (Throwable e) {
            return false;
        }
    }

    @Override
    public boolean acquireLock(String key, String value) {
        if (ObjectUtil.isNullOrEmptyStr(key)) {
            return false;
        }

        try (Jedis jedis = jedisPool.getResource()) {
            Long result = jedis.setnx(getFullKeyWithSerialize(key), serializeValue(value));
            return result == 1;
        } catch (Throwable e) {
            return false;
        }
    }

    @Override
    public long pttl(String key) {
        if (ObjectUtil.isNullOrEmptyStr(key)) {
            throw new CacheException("the param key must not be empty");
        }

        try (Jedis jedis = jedisPool.getResource()) {
            //-1 表示无过期时间，-2表示缓存不存在或已过期
            return jedis.pttl(getFullKeyWithSerialize(key));
        } catch (Throwable e) {
            throw new CacheException(e);
        }
    }
}
