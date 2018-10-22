package zlCache.factory.redis;

import org.springframework.util.CollectionUtils;
import zlCache.exception.CacheException;
import zlCache.factory.AbstractCacheClient;
import zlCache.factory.redis.operations.pubsub.Subscription;
import zlCache.util.ObjectUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import redis.clients.jedis.BinaryJedisPubSub;
import redis.clients.jedis.ScanParams;
import redis.clients.jedis.ScanResult;
import redis.clients.jedis.Tuple;
import redis.clients.util.SafeEncoder;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @author xier
 * @date 2018/10/7 10:41
 */
public abstract class AbstractRedisClient extends AbstractCacheClient implements RedisClientIF
{
    private static final Logger log = LoggerFactory.getLogger(AbstractRedisClient.class);

    private static final byte[] REMOVE_KEYS_BY_PATTERN_LUA = stringSerializer.serialize("local keys = redis.call('KEYS', ARGV[1]); local keysCount = table.getn(keys); if(keysCount > 0) then for _, key in ipairs(keys) do redis.call('del', key); end; end; return keysCount;");

    private static final byte[] WILD_CARD = stringSerializer.serialize("*");

    protected abstract <T> T execute(RedisCallback<T> action);

    protected abstract <T> T executeInUseRawJedis(RedisCallback<T> action);

    protected byte[] getFullKeyWithSerialize(String key) {
        return serializeKey(getFullKey(key));
    }


    /**
     * cleanKeysByPrefix
     *
     * @see "org.springframework.data.redis.cache.RedisCacheCleanByPrefixCallback"
     */
    @Override
    public boolean cleanKeysByPrefix(String prefix) {
        if (ObjectUtil.isNullOrEmptyStr(prefix)) {
            throw new CacheException("prefix Can not be null or empty");
        }

        byte[] prefixBytes = getFullKeyWithSerialize(prefix);

        byte[] prefixToUse = Arrays.copyOf(prefixBytes, prefixBytes.length + WILD_CARD.length);
        System.arraycopy(WILD_CARD, 0, prefixToUse, prefixBytes.length, WILD_CARD.length);

        Object result = execute(jedis -> jedis.eval(REMOVE_KEYS_BY_PATTERN_LUA, 0, prefixToUse)); // 返回删除条数
        log.info("cleanKeysByPrefix, prefix: {}, result: {}", prefix, result);
        return true;
    }

    @Override
    public Set<String> keys(String pattern) {
        if (ObjectUtil.isNullOrEmptyStr(pattern)) {
            throw new CacheException("prefix Can not be null or empty");
        }

        byte[] prefixBytes = getFullKeyWithSerialize(pattern);
        return execute(jedis -> {
            Set<byte[]> keyByteSet = jedis.keys(prefixBytes);
            if (null == keyByteSet || keyByteSet.size() == 0)
            {
                return null;
            }

            return keyByteSet.stream().map(bytes -> removeKeyPrefix(deserializeKey(bytes))).collect(Collectors.toSet());
        });
    }

    @Override
    public <T> T getSet(String key, String value) {
        if(ObjectUtil.isNullOrEmptyStr(key)) {
            return null;
        }

        return execute(jedis -> {
            byte[] result = jedis.getSet(getFullKeyWithSerialize(key), serializeValue(value));

            if (ObjectUtil.isNull(result))
            {
                return null;
            }
            return (T) deserializeValue(result);
        });
    }

    @Override
    public Boolean expire(String key, int second) {
        return execute(jedis -> {
            return jedis.expire(getFullKeyWithSerialize(key), second) > 0;
        });
    }

    @Override
    public <T> T hGet(String key, String field) {
        return execute(jedis -> {
            byte[] bytes = jedis.hget(getFullKeyWithSerialize(key), serializeKey(field));
            if (null != bytes) {
                return deserializeValue(bytes);
            }
            return null;
        });
    }

    @Override
    public Long hDel(String key, String... fields) {
        return execute(jedis -> {
            byte[][] fieldBytes = new byte[fields.length][];
            int i = 0;
            for (String field : fields) {
                fieldBytes[i++] = serializeKey(field);
            }
            return jedis.hdel(getFullKeyWithSerialize(key), fieldBytes);
        });
    }

    @Override
    public Boolean hExists(String key, String field)
    {
        return execute(jedis -> jedis.hexists(getFullKeyWithSerialize(key), serializeKey(field)));
    }

    @Override
    public <T> Map<String, T> hGetAll(String key) {
        return execute(jedis -> {
            Map<String, T> map = new HashMap<>();
            Map<byte[], byte[]> resultMap = jedis.hgetAll(getFullKeyWithSerialize(key));
            if (null == resultMap || resultMap.size() == 0) {
                return null;
            }
            for (byte[] keyBytes : resultMap.keySet()) {
                map.put(deserializeKey(keyBytes), deserializeValue(resultMap.get(keyBytes)));
            }
            return map;
        });
    }

    @Override
    public Set<String> hKeys(String key) {
        return execute(jedis -> {
            Set<String> keyStrSet = new LinkedHashSet<>();

            Set<byte[]> keyByteSet = jedis.hkeys(getFullKeyWithSerialize(key));
            if (null == keyByteSet || keyByteSet.size() == 0) {
                return null;
            }

            for (byte[] keyByte : keyByteSet) {
                keyStrSet.add(deserializeKey(keyByte));
            }
            return keyStrSet;
        });
    }

    @Override
    public <T> List<T> hMGet(String key, String... fields) {
        return execute(jedis -> {
            List<T> fieldValueList = new ArrayList<>();

            byte[][] fieldBytes = new byte[fields.length][];
            int i = 0;
            for (String field : fields) {
                fieldBytes[i++] = serializeKey(field);
            }
            List<byte[]> fieldValueByteList = jedis.hmget(getFullKeyWithSerialize(key), fieldBytes);
            if (null == fieldValueByteList || fieldValueByteList.size() == 0) {
                return fieldValueList;
            }

            for (byte[] fieldValueByte : fieldValueByteList) {
                if (null != fieldValueByte) {
                    fieldValueList.add(deserializeValue(fieldValueByte));
                }
            }
            return fieldValueList;
        });
    }

    @Override
    public Long hSet(String key, String field, Object fieldValue)
    {
        return execute(jedis ->
        {
            return jedis.hset(getFullKeyWithSerialize(key), serializeKey(field), serializeValue(fieldValue));
        });
    }

    @Override
    public Boolean hMSet(String key, Map<String, Object> hashMap) {
        return execute(jedis -> {
            Map<byte[], byte[]> byteMap = new HashMap<>();
            for (Map.Entry<String, Object> entry : hashMap.entrySet()) {
                byteMap.put(serializeKey(entry.getKey()), serializeValue(entry.getValue()));
            }

            String result = jedis.hmset(getFullKeyWithSerialize(key), byteMap);
            return "OK".equals(result);
        });
    }

    @Override
    public <T> T lPop(String key) {
        return execute(jedis -> {
            byte[] bytes = jedis.lpop(getFullKeyWithSerialize(key));
            if (null != bytes) {
                return deserializeValue(bytes);
            }
            return null;
        });
    }

    @Override
    public Long lPush(String key, Object... values) {
        return execute(jedis -> {
            byte[][] bytes = new byte[values.length][];
            int i = 0;
            for (Object value : values) {
                bytes[i++] = serializeValue(value);
            }

            return jedis.lpush(getFullKeyWithSerialize(key), bytes);
        });
    }

    @Override
    public <T> List<T> lRange(String key, final long start, final long end) {
        return execute(jedis -> {
            List<byte[]> byteArrList = jedis.lrange(getFullKeyWithSerialize(key), start, end);
            if (null == byteArrList) {
                return null;
            }
            List<T> list = new ArrayList<T>();
            for (byte[] value : byteArrList) {
                list.add(deserializeValue(value));
            }
            return list;
        });
    }

    @Override
    public Long sAdd(String key, Object value) {
        return execute(jedis -> {
            return jedis.sadd(getFullKeyWithSerialize(key), serializeValue(value));
        });
    }

    @Override
    public Long sAdd(String key, Object... values) {
        return execute(jedis -> {
            byte[][] valueBytes = new byte[values.length][];
            int i = 0;
            for (Object value : values) {
                valueBytes[i++] = serializeValue(value);
            }
            return jedis.sadd(getFullKeyWithSerialize(key), valueBytes);
        });
    }

    @Override
    public Boolean sIsMember(String key, Object value) {
        return execute(jedis -> {
            return jedis.sismember(getFullKeyWithSerialize(key), serializeValue(value));
        });
    }

    @Override
    public <T> Set<T> sMembers(String key) {
        return execute(jedis -> {
            Set<byte[]> byteArrSets = jedis.smembers(getFullKeyWithSerialize(key));
            if (null == byteArrSets) {
                return null;
            }
            Set<T> set = new HashSet<T>();
            for (byte[] byteArr : byteArrSets) {
                set.add(deserializeValue(byteArr));
            }
            return set;
        });
    }

    @Override
    public Long sRem(String key, Object value) {
        return execute(jedis -> {
            return jedis.srem(getFullKeyWithSerialize(key), serializeValue(value));
        });
    }

    @Override
    public Long sCard(String key) {
        return execute(jedis -> {
            return jedis.scard(getFullKeyWithSerialize(key));
        });
    }

    @Override
    public Long zAdd(String key, String member, double score)
    {
        return execute(jedis -> jedis.zadd(getFullKeyWithSerialize(key), score, serializeValue(member)));
    }

    @Override
    public Long zRem(String key, String... members) {
        byte[][] valueBytes = new byte[members.length][];
        int i = 0;
        for (Object value : members) {
            valueBytes[i++] = serializeValue(value);
        }
        return execute(jedis -> jedis.zrem(getFullKeyWithSerialize(key), valueBytes));
    }

    @Override
    public Long zCard(String key)
    {
        return execute(jedis -> jedis.zcard(getFullKeyWithSerialize(key)));
    }

    @Override
    public Long zCount(String key, double min, double max)
    {
        return execute(jedis -> jedis.zcount(getFullKeyWithSerialize(key), min, max));
    }

    @Override
    public Set<Map.Entry<String, Double>> zRangeWithScores(String key, long start, long end)
    {
        return execute(jedis -> {
            return convertTupleSetToEntrySet(jedis.zrangeWithScores(getFullKeyWithSerialize(key), start, end));
        });
    }

    @Override
    public Set<Map.Entry<String, Double>> zRevRangeWithScores(String key, long start, long end)
    {
        return execute(jedis -> {
            return convertTupleSetToEntrySet(jedis.zrevrangeWithScores(getFullKeyWithSerialize(key), start, end));
        });
    }

    @Override
    public Set<Map.Entry<String, Double>> zRangeByScoreWithScores(String key, double min, double max, int offset, int count)
    {
        return execute(jedis ->
        {
            return convertTupleSetToEntrySet(jedis.zrangeByScoreWithScores(getFullKeyWithSerialize(key), min, max, offset, count));
        });
    }

    @Override
    public Set<Map.Entry<String, Double>> zRangeByScoreWithScores(String key, String min, String max, int offset, int count)
    {
        return execute(jedis ->
        {
            return convertTupleSetToEntrySet(jedis.zrangeByScoreWithScores(getFullKeyWithSerialize(key), SafeEncoder.encode(min), SafeEncoder.encode(max), offset, count));
        });
    }

    @Override
    public Set<Map.Entry<String, Double>> zRevRangeByScoreWithScores(String key, double min, double max, int offset, int count)
    {
        return execute(jedis ->
        {
            return convertTupleSetToEntrySet(jedis.zrevrangeByScoreWithScores(getFullKeyWithSerialize(key), max, min, offset, count));
        });
    }

    private Set<Map.Entry<String, Double>> convertTupleSetToEntrySet(Set<Tuple> tupleSet) {
        if (CollectionUtils.isEmpty(tupleSet)) {
            return Collections.emptySet();
        }

        Set<Map.Entry<String, Double>> resultSet = new LinkedHashSet<>();
        for (Tuple tuple : tupleSet) {
            Map.Entry<String, Double> entry = new AbstractMap.SimpleEntry(deserializeValue(tuple.getBinaryElement()), tuple.getScore());
            resultSet.add(entry);
        }
        return resultSet;
    }

    @Override
    public Long publish(String channel, Object message) {
        return execute(jedis -> {
            return jedis.publish(getFullKeyWithSerialize(channel), serializeValue(message));
        });
    }

    /**
     * 注意会一直堵塞，直到取消订阅
     * 当出现异常时，会关闭连接
     *
     * @param subscription
     * @param channels     channel names
     */
    @Override
    public void subscribe(Subscription subscription, String... channels) {
        executeInUseRawJedis(jedis -> {
            byte[][] bytes = new byte[channels.length][];
            int i = 0;
            for (String channel : channels) {
                bytes[i++] = getFullKeyWithSerialize(channel);
            }

            BinaryJedisPubSub binaryJedisPubSub = new BinaryJedisPubSub() {
                public void onMessage(byte[] channel, byte[] message) {
                    String chan = removeKeyPrefix(deserializeKey(channel));
                    Object msg = deserializeValue(message);

                    subscription.getMessageListener().onMessage(chan, msg, null);
                }

                public void onPMessage(byte[] pattern, byte[] channel, byte[] message) {
                    String patt = removeKeyPrefix(deserializeKey(pattern));
                    String chan = removeKeyPrefix(deserializeKey(channel));
                    Object msg = deserializeValue(message);

                    subscription.getMessageListener().onMessage(chan, msg, patt);
                }
            };
            subscription.storeBinaryJedisPubSub(binaryJedisPubSub);
            jedis.subscribe(binaryJedisPubSub, bytes);
            return true;
        });
    }

    @Override
    public void pSubscribe(Subscription subscription, String... patterns) {
        executeInUseRawJedis(jedis -> {
            byte[][] bytes = new byte[patterns.length][];
            int i = 0;
            for (String pattern : patterns)
            {
                bytes[i++] = getFullKeyWithSerialize(pattern);
            }

            BinaryJedisPubSub binaryJedisPubSub = new BinaryJedisPubSub() {
                public void onMessage(byte[] channel, byte[] message) {
                    String chan = removeKeyPrefix(deserializeKey(channel));
                    Object msg = deserializeValue(message);

                    subscription.getMessageListener().onMessage(chan, msg, null);
                }

                public void onPMessage(byte[] pattern, byte[] channel, byte[] message) {
                    String patt = removeKeyPrefix(deserializeKey(pattern));
                    String chan = removeKeyPrefix(deserializeKey(channel));
                    Object msg = deserializeValue(message);

                    subscription.getMessageListener().onMessage(chan, msg, patt);
                }
            };
            subscription.storeBinaryJedisPubSub(binaryJedisPubSub);
            jedis.psubscribe(binaryJedisPubSub, bytes);
            return true;
        });
    }

    @Override
    public void unsubscribe(Subscription subscription, String... channels) {
        try {
            byte[][] bytes = new byte[channels.length][];
            int i = 0;
            for (String channel : channels) {
                bytes[i++] = getFullKeyWithSerialize(channel);
            }

            subscription.getBinaryJedisPubSub().unsubscribe(bytes);
        } catch (Throwable e) {
            throw new CacheException(e);
        }

    }

    @Override
    public void pUnsubscribe(Subscription subscription, String... patterns) {
        try {
            byte[][] bytes = new byte[patterns.length][];
            int i = 0;
            for (String pattern : patterns) {
                bytes[i++] = getFullKeyWithSerialize(pattern);
            }

            subscription.getBinaryJedisPubSub().punsubscribe(bytes);
        } catch (Throwable e) {
            throw new CacheException(e);
        }
    }

    @Override
    public Long lLen(String key)
    {
        return execute(jedis -> jedis.llen(getFullKey(key)));
    }

    @Override
    public Set<String> scan(String pattern) {
        if (ObjectUtil.isNullOrEmptyStr(pattern)) {
            throw new CacheException("prefix Can not be null or empty");
        }

        byte[] prefixBytes = getFullKeyWithSerialize(pattern);
        return execute(jedis -> {
            List<byte[]> allKeys = new ArrayList<>();

            ScanParams scanParams = new ScanParams();
            scanParams.match(prefixBytes);
            scanParams.count(3000);

            //定义起始游标，游标以0开始，以0结束
            String beginCursor = "0", cursor = "0";

            while(true) {
                ScanResult<byte[]> result = jedis.scan(cursor.getBytes(), scanParams);

                List<byte[]> keys = result.getResult();
                if(keys != null) {
                    allKeys.addAll(keys);
                }
                cursor = new String(result.getCursorAsBytes());

                //游标变为0时，结束
                if (beginCursor.equals(cursor)) {
                    break;
                }
            }

            if (allKeys.size() == 0) {
                return null;
            }
            return allKeys.stream().map(bytes -> removeKeyPrefix(deserializeKey(bytes))).collect(Collectors.toSet());
        });
    }

    /**
     * 获取redis状态
     * @return
     */
    @Override
    public String status()
    {
        return execute(jedis -> {
            return jedis.info();
        });
    }

    @Override
    public Boolean exists(String key) {
        return execute(jedis -> {
            return jedis.exists(getFullKeyWithSerialize(key));
        });
    }
}
