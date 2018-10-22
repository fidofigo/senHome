package zlCache.localcache;

import java.io.Serializable;

/**
 * @author xier
 * @date 2018/10/8 11:56
 */
public class LocalCacheBO implements Serializable
{
    private static final long serialVersionUID = 5408463761510131328L;

    /***
     *  传进来的key值
     */
    private String key;

    /***
     *  存放时间（毫秒）
     */
    private long createTime;

    /***
     *  缓存时间（秒）
     */
    private long cacheTime;

    /***
     *  缓存的对象
     */
    private Object object;


    public String getKey()
    {
        return key;
    }

    public void setKey(String key)
    {
        this.key = key;
    }

    public long getCreateTime()
    {
        return createTime;
    }

    public void setCreateTime(long createTime)
    {
        this.createTime = createTime;
    }

    public long getCacheTime()
    {
        return cacheTime;
    }

    public void setCacheTime(long cacheTime)
    {
        this.cacheTime = cacheTime;
    }

    public Object getObject()
    {
        return object;
    }

    public void setObject(Object object)
    {
        this.object = object;
    }
}
