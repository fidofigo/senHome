package com.senhome.shell.common.zlCache.factory.redis.operations;

import java.util.Map;
import java.util.Set;

/**
 * @author xier
 * @date 2018/10/7 10:46
 */
public interface ZSetOperations
{
    /**
     * 添加
     */
    Long zAdd(String key, String member, double score);

    /**
     * 删除
     * @param key
     * @param members
     * @return
     */
    Long zRem(String key, String... members);

    /**
     * 统计所有元素个数
     * @param key
     * @return
     */
    Long zCard(String key);

    /**
     * 统计元素 min<=score<=max 的个数
     * @param key
     * @param min
     * @param max
     * @return
     */
    Long zCount(String key, double min, double max);

    /**
     * 查询 select member,score from `key_zset` where 1 = 1 order by score asc
     * @param key
     * @param start
     * @param end
     * @return
     */
    Set<Map.Entry<String, Double>> zRangeWithScores(String key, long start, long end);

    /**
     * 查询 select member,score from `key_zset` where 1 = 1 order by score desc
     * @param key
     * @param start
     * @param end
     * @return
     */
    Set<Map.Entry<String, Double>> zRevRangeWithScores(String key, long start, long end);

    /**
     * 查询 select member,score from `key_zset` where min<=score<=max limit offset count order by score asc
     * @param key
     * @param min
     * @param max
     * @param offset
     * @param count
     * @return
     */
    Set<Map.Entry<String, Double>> zRangeByScoreWithScores(String key, double min, double max, int offset, int count);

    /**
     * min max 支持-inf  +inf
     * @param key
     * @param min
     * @param max
     * @param offset
     * @param count
     * @return
     */
    Set<Map.Entry<String, Double>> zRangeByScoreWithScores(String key, String min, String max, int offset, int count);

    /**
     * 查询 select member,score from `key_zset` where min<=score<=max limit offset count order by score desc
     * @param key
     * @param min
     * @param max
     * @param offset
     * @param count
     * @return
     */
    Set<Map.Entry<String, Double>> zRevRangeByScoreWithScores(String key, double min, double max, int offset, int count);
}
