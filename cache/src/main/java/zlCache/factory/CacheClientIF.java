package zlCache.factory;

import java.util.List;
import java.util.Map;
import java.util.function.Supplier;
import zlCache.exception.CacheException;

/**
 * @author xier
 * @date 2018/10/7 10:24
 */
public interface CacheClientIF extends BaseCacheClientIF, DistributedLockIF
{
    /**
     * 根据Key,获得value ，参数不能为空否则报异常(runtimeException)。
     *
     * redis redis也是使用的kryo进行的反序列化，存在OOM的可能性，所以调用缓存也请不要for循环get单个key，请使用下方的批 量查询。
     * </pre>
     *
     * @param key
     * @return T 返回值可能是null，需要客户端自己进行判断。
     * @throws CacheException
     */
    <T> T get(String key);

    byte[]  getRawBytes(String key);

    /**
     * 获取incr或decr的之后的值,切记不能使用get方法,一定要使用此方法
     *
     * @param key
     * @return Long
     * @throws CacheException
     */
    Long getLong(String key);

    /****
     * 先获取，如果不存在，就set 。函数式编程
     *
     * @param key
     * @param supplier
     * @param expireTime
     * @return
     * @throws CacheException
     */
    <T> T getOrCreate(String key, Supplier<T> supplier, int expireTime);

    <T> T getOrCreate(String key, Supplier<T> supplier);

    <T> T getOrCreateWithSyncLock(String key, Supplier<T> supplier, int expireTime);

    /**
     * 根据key集合批量获取
     *
     * @param keys
     * @return Map<String,Object>  如果key不存在,会返回null
     * @throws CacheException
     */
    Map<String, Object> getAllByKeys(List<String> keys);

    /**
     * 放入缓存的方法
     *
     * @param key
     * @param value
     * @param second  秒
     * @throws CacheException
     */
    void put(String key, Object value, int second);

    /**
     * bytes 不会再序列化
     * 需要通过getRawBytes获取
     * @param key
     * @param bytes
     * @param second
     */
    void putBytes(String key, byte[] bytes, int second);

    /**
     * 根据key删除一个值
     * @param key
     * @return boolean
     * @throws CacheException
     */
    boolean delete(String key);

    /**
     * 删除多个key
     *
     * @return boolean
     * @throws CacheException
     */
    boolean delete(List<String> keys);

    /**
     * 加法
     * @param key
     * @param delta
     * @return long 返回加之后的值
     * @throws CacheException
     */
    long incr(String key, long delta);

    /**
     * 减法
     * @param key
     * @param decrement
     * @return long 返回减之后的值
     * @throws CacheException
     */
    long decr(String key, int decrement);

    /**
     * 获取缓存的有效时间 (单位毫秒)
     * memcached暂时不支持
     *
     * @param key       缓存key
     * @return          有效时间
     *  -1：没有设置过期时间
     *  -2：缓存不存在、已过期、无法获取缓存时间
     */
    long pttl(String key);
}
