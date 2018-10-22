package zlCache.factory;

import java.util.function.Supplier;

/**
 * @author xier
 * @date 2018/10/7 10:24
 */
public abstract class AbstractCacheClient implements CacheClientIF
{
    @Override
    public String getPrefix()
    {
        return null;
    }

    @Override
    public byte[] serializeKey(Object key) {
        return null;
    }

    @Override
    public <T> T deserializeKey(byte[] byteKey)
    {
        return null;
    }

    @Override
    public byte[] serializeValue(Object value)
    {
        return null;
    }

    @Override
    public <T> T deserializeValue(byte[] byteValue)
    {
        return null;
    }

    @Override
    public boolean acquireLock(String key, int ttl) {
        return false;
    }

    @Override
    public boolean releaseLock(String key)
    {
        return false;
    }

    @Override
    public <T> T getOrCreateWithSyncLock(String key, Supplier<T> supplier, int expireTime) {
        throw new UnsupportedOperationException("暂时不支持");
    }

    @Override
    public boolean acquireLock(String key, String value)
    {
        return false;
    }

    @Override
    public void putBytes(String key, byte[] bytes, int second)
    {
        throw  new  UnsupportedOperationException("暂时不支持");
    }

    @Override
    public byte[] getRawBytes(String key)
    {
        throw  new  UnsupportedOperationException("暂时不支持");
    }
}
