package zlCache.localcache;

import com.google.common.base.Strings;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import zlCache.constant.Constants;
import zlCache.exception.CacheException;
import zlCache.factory.AbstractCacheClient;
import zlCache.support.holder.SyncLockHolder;
import zlCache.util.CacheUtil;
import zlCache.util.ObjectUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;
import java.util.function.Supplier;

/**
 * @author xier
 * @date 2018/10/8 11:56
 */
public class LocalCacheClientImpl extends AbstractCacheClient
{
    private static final Logger log = LoggerFactory.getLogger(LocalCacheClientImpl.class);

    /**
     * guava缓存
     */
    private LoadingCache<String, Object> loadingCache;

    /**
     * 缓存时间（秒为单位）
     */
    private long initCacheTime = Constants.ONE_DAY * 3;

    /**
     * 无参构造方法
     */
    public LocalCacheClientImpl()
    {
        // 初始化方法
        init();
    }

    // 初始化方法
    private void init()
    {
        loadingCache = CacheBuilder.newBuilder()
            .maximumSize(CacheUtil.calcLocalCacheKeyNumByJvmHeapSize())
            .expireAfterWrite(initCacheTime, TimeUnit.SECONDS)
            .build(new CacheLoader<String, Object>() {
                @Override
                public String load(String String) {
                    return Constants.LOCAL_CACHE_LOAD_DEFAULT_VALUE;
                }
            });
    }

    @Override
    public void put(String key, Object value, int second)
    {
        // 判断key是否为空
        if (Strings.isNullOrEmpty(key)) {
            throw new CacheException("key Can not be empty or null");
        }
        // 判断value是否为空
        if (ObjectUtil.isNull(value)) {
            // throw new CacheException("value Can not be null");
            return;
        }
        // 判断要设置的缓存时间是否大于缓存实例时间。
        if (initCacheTime < second) {
            throw new CacheException("过期时间不能大于缓存实例时间:" + initCacheTime + "秒");
        }
        LocalCacheBO cacheBO = new LocalCacheBO();
        cacheBO.setCreateTime(System.currentTimeMillis());
        cacheBO.setKey(key);
        cacheBO.setObject(value);
        cacheBO.setCacheTime(second);
        loadingCache.put(key, cacheBO);
    }

    @SuppressWarnings("unchecked")
    @Override
    public <T> T get(String key)
    {
        // 判断是否为空
        if (Strings.isNullOrEmpty(key)) {
            throw new CacheException("key Can not be empty or null");
        }
        try {
            // 得到数据
            Object object = loadingCache.get(key);
            if (Objects.isNull(object) || Constants.LOCAL_CACHE_LOAD_DEFAULT_VALUE.equals(object)) {
                return null;
            }
            LocalCacheBO cacheBO = (LocalCacheBO)object;
            if (isExpire(cacheBO)) {
                loadingCache.invalidate(key);
                return null;
            }
            return (T)cacheBO.getObject();
        } catch (Exception e) {
            throw new CacheException(e);
        }
    }

    private Boolean isExpire(LocalCacheBO localCacheBO) {
        if (null == localCacheBO) {
            return true;
        }
        if (System.currentTimeMillis() - localCacheBO.getCreateTime() > localCacheBO.getCacheTime() * 1000) {
            return true;
        }
        return false;
    }

    @Override
    public boolean delete(String key) {
        // 判断是否为空
        if (Strings.isNullOrEmpty(key)) {
            throw new CacheException("key Can not be empty or null");
        }
        loadingCache.invalidate(key);
        return true;

    }

    @Override
    public Map<String, Object> getAllByKeys(List<String> keys) {
        if (ObjectUtil.isNullOrEmpty(keys)) {
            throw new CacheException("keys Can not be null or length be 0");
        }

        try {
            Map<String, Object> map = loadingCache.getAll(keys);
            if (null == map || map.size() == 0) {
                return null;
            }

            Map<String, Object> resultMap = new LinkedHashMap<>();
            for (String key : keys) {
                Object object = map.get(key);
                if (Objects.isNull(object) || Constants.LOCAL_CACHE_LOAD_DEFAULT_VALUE.equals(object)) {
                    resultMap.put(key, null);
                    continue;
                }
                LocalCacheBO cacheBO = (LocalCacheBO)object;
                if (isExpire(cacheBO)) {
                    resultMap.put(key, null);
                    continue;
                }
                resultMap.put(key, cacheBO.getObject());
            }

            return resultMap;
        } catch (Exception e) {
            throw new CacheException(e);
        }
    }

    @Override
    public boolean delete(List<String> keys) {
        if (ObjectUtil.isNullOrEmpty(keys)) {
            throw new CacheException("keys Can not be empty or null");
        }
        loadingCache.invalidateAll(keys);
        return true;
    }

    @Override
    public long incr(String key, long delta) {
        throw new UnsupportedOperationException("This method is not supported for the time being");
    }

    @Override
    public long decr(String key, int decrement) {
        throw new UnsupportedOperationException("This method is not supported for the time being");
    }

    @Override
    public Long getLong(String key) {
        throw new UnsupportedOperationException("This method is not supported for the time being");
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
    public String getPrefix() {
        return "";
    }

    @Override
    public <T> T getOrCreateWithSyncLock(String key, Supplier<T> supplier, int expireTime)
    {
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
    public long pttl(String key) {
        // 判断是否为空
        if (Strings.isNullOrEmpty(key)) {
            throw new CacheException("key Can not be empty or null");
        }
        try {
            // 得到数据
            Object object = loadingCache.get(key);
            if (Objects.isNull(object) || Constants.LOCAL_CACHE_LOAD_DEFAULT_VALUE.equals(object)) {
                return -2;
            }
            LocalCacheBO cacheBO = (LocalCacheBO)object;
            if (isExpire(cacheBO)) {
                loadingCache.invalidate(key);
                return -2;
            }

            //计算缓存的剩余有效时间
            return (cacheBO.getCacheTime() * 1000) - (System.currentTimeMillis() - cacheBO.getCreateTime());
        }catch (Exception e) {
            throw new CacheException(e);
        }
    }
}
