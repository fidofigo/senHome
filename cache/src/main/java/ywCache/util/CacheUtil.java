package ywCache.util;

import ywCache.constant.Constants;

import java.lang.management.ManagementFactory;
import java.lang.management.RuntimeMXBean;

/**
 * @author xier
 * @date 2018/10/8 13:42
 */
public class CacheUtil
{
    /**
     * 计算本地缓存 key的个数
     *
     * @return long
     */
    public static long calcLocalCacheKeyNumByJvmHeapSize() {
        long maxHeapByteSize = maxHeapByteSize();
        if (maxHeapByteSize <= 0) {
            return Constants.LOCAL_CACHE_DEFAULT_KEY_NUM;
        }
        return Math.round(maxHeapByteSize * Constants.LOCAL_CACHE_MEM_RATIO / Constants.LOCAL_CACHE_DEFAULT_KEY_SIZE);
    }


    /**
     * 获取jvm 最大堆内存
     *
     * @return long 单位B
     */
    private static long maxHeapByteSize() {
        long maxHeapSize = ManagementFactory.getMemoryMXBean().getHeapMemoryUsage().getMax();

        if (maxHeapSize <= 0) {
            RuntimeMXBean runtimeMxBean = ManagementFactory.getRuntimeMXBean();
            for (String option : runtimeMxBean.getInputArguments()) {
                option = option.trim().toLowerCase();
                if (option.startsWith("-xmx") && option.endsWith("m")) {
                    maxHeapSize = Long.valueOf(option.replace("-xmx", "").replace("m", "")) * 1024 * 1024;
                    break;
                }
            }
        }

        return maxHeapSize;
    }
}
