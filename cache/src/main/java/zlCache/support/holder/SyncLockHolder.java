package zlCache.support.holder;

import com.googlecode.concurrentlinkedhashmap.Weighers;
import com.googlecode.concurrentlinkedhashmap.ConcurrentLinkedHashMap;
import zlCache.util.ObjectUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author xier
 * @date 2018/10/7 11:09
 */
public class SyncLockHolder
{
    private static final Logger log = LoggerFactory.getLogger(SyncLockHolder.class);

    private static volatile ConcurrentMap<String, ReentrantLock>
        lockMap = new ConcurrentLinkedHashMap.Builder<String, ReentrantLock>().maximumWeightedCapacity(10240).weigher(Weighers.singleton()).listener((key, value) -> {
        if (log.isDebugEnabled()) {
            log.debug("key: {}, value: {} is remove", key, value);
        }
    }).build();

    /**
     * 非线程安全, 在高并发下, 有可能出现一个key对应几个lock
     *
     * @param key
     * @return
     */
    public static ReentrantLock getLockByKey(String key) {
        if (ObjectUtil.isNullOrEmptyStr(key)) {
            return null;
        }

        ReentrantLock lock = lockMap.get(key);
        if (null == lock) {
            lock = new ReentrantLock(true);
            lockMap.putIfAbsent(key, lock);
        }
        return lock;
    }
}
