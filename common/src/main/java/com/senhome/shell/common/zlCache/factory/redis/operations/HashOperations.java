package com.senhome.shell.common.zlCache.factory.redis.operations;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author xier
 * @date 2018/10/7 10:42
 */
public interface HashOperations
{
    /**
     * 返回哈希表 key 中给定域 field 的值
     * @param key
     * @param field
     * @param <T>
     * @return 给定域的值 或者 不存在时返回null
     */
    <T> T hGet(String key, String field);

    /**
     * 返回哈希表 key 中，所有的域和值
     * @param key
     * @param <T>
     * @return map 或者 如果key不存在时返回null
     */
    <T> Map<String, T> hGetAll(String key);

    /**
     * 查看哈希表 key 中，给定域 field 是否存在
     * @param key
     * @param field
     * @return 是否存在
     */
    Boolean hExists(String key, String field);

    /**
     * 删除哈希表 key 中的一个或多个指定域，不存在的域将被忽略
     * @param key
     * @param fields
     * @return 被成功移除的域的数量，不包括被忽略的域。 如果没有任何移除返回0
     */
    Long hDel(String key, String... fields);

    /**
     * 返回所有field
     * @param key
     * @return set or 如果key不存在返回null
     */
    Set<String> hKeys(String key);

    /**
     * @param key
     * @param fields
     * @param <T>
     * @return 如果field不存在，不会在list中返回null的，如果key不存在会返回一个空的list
     */
    <T> List<T> hMGet(String key, String... fields);

    /**
     * 如果 key 不存在，一个新的哈希表被创建并进行 HSET 操作，返回1
     * 如果 field 是哈希表中的一个新建域，并且值设置成功，返回 1
     * 如果哈希表中域 field 已经存在且旧值已被新值覆盖，返回 0
     * @param key
     * @param field
     * @param fieldValue
     * @return
     */
    Long hSet(String key, String field, Object fieldValue);

    /**
     * 如果 key 不存在，一个空哈希表被创建并执行 HMSET 操作
     * 此命令会覆盖哈希表中已存在的field
     * 当 key 不是哈希表(hash)类型时，返回异常
     * @param key
     * @param hashMap
     * @return
     */
    Boolean hMSet(String key, Map<String, Object> hashMap);
}
