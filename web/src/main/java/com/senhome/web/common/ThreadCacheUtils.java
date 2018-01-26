package com.senhome.web.common;

import com.senhome.shell.common.dal.domain.ProtocolPojo;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * 缓存数据到本地线程变量中
 *
 * @author <a href="mailto:zhongchao@gegejia.com">zhong</a>
 * @version 1.0 2016/11/26
 * @since 1.0
 */
public class ThreadCacheUtils
{
    private static final ThreadLocal<Map<String, Object>> THREAD_LOCAL = new ThreadLocal<>();

    public static void put(String key, Object value)
    {
        Map<String, Object> map = Optional.ofNullable(THREAD_LOCAL.get()).orElse(new HashMap<>());
        map.put(key, value);
        THREAD_LOCAL.set(map);
    }
    
    public static <T> T get(String key)
    {
        Map<String, Object> map = THREAD_LOCAL.get();
        if (map == null)
        {
            return null;
        }

        return (T) map.get(key);
    }

    public static void setProtocol(ProtocolPojo protocol) {
        put("protocol", protocol);
    }

    public static <T> T getProtocol() {
        return get("protocol");
    }

    public static void remove()
    {
        THREAD_LOCAL.remove();
    }

    public static <T> T getAccount()
    {
        return get("collect");
    }

}
