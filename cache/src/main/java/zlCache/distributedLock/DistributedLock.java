package zlCache.distributedLock;

import zlCache.factory.redis.RedisClientIF;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;

/**
 * 分布式锁
 * @author xier
 * @date 2018/10/7 10:34
 */
@Slf4j
public class DistributedLock implements Lock
{
    /**
     * 锁拥有者
     */
    private Thread ownerThread;

    /**
     * redis客户端
     */
    private RedisClientIF redisClientIF;

    /**
     * 状态
     */
    private volatile boolean locked;

    /**
     * 锁超时时间
     */
    private long lockExpire;

    /**
     * 锁名称
     */
    private String lockKey;

    public DistributedLock(RedisClientIF redisClientIF, String lockKey, long lockExpire)
    {
        this.redisClientIF = redisClientIF;
        this.lockKey = lockKey;
        this.lockExpire = lockExpire;
    }

    @Override
    public void lock()
    {
        try {
            lock(false, 0, null, false);
        } catch (InterruptedException ex) {
            //ignore
        }
    }

    @Override
    public void lockInterruptibly()
        throws InterruptedException {
        lock(false, 0, null, false);
    }

    @Override
    public boolean tryLock()
    {
        //锁超时时间
        long lockExpireTime = System.currentTimeMillis() + lockExpire + 1;
        String strOfLockExpireTime = String.valueOf(lockExpireTime);

        //获取锁
        if (redisClientIF.acquireLock(lockKey, strOfLockExpireTime)) {
            //获取到锁标识
            locked = true;
            //设置当前获取锁的线程
            this.ownerThread = Thread.currentThread();
            return true;
        }

        //获取不到锁处理
        String value = redisClientIF.get(lockKey);

        //锁过期. 如果锁没过期，则进入下次循环
        if(value != null && isTimeExpired(value) && locked) {
            //假设多个线程总到此处, getAndSet使用redis的getSet方法（原子性的）。返回原先的旧值，如果获取到的旧值依然是过期，则表示获取到锁
            String oldValue = redisClientIF.getSet(lockKey, strOfLockExpireTime);

            if(oldValue != null && oldValue.equals(value)) {
                locked = true;

                this.ownerThread = Thread.currentThread();
                return true;
            }
        }

        return false;
    }

    @Override
    public boolean tryLock(long time, TimeUnit unit)
    {
        try {
            return lock(true, time, unit, false);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * 阻塞式获取锁的实现
     *
     * @param useTimeout
     * @param time
     * @param unit
     * @param interrupt
     *            是否响应中断
     * @return
     * @throws InterruptedException
     */
    protected boolean lock(boolean useTimeout, long time, TimeUnit unit, boolean interrupt)
        throws InterruptedException {
        if(interrupt) {
            checkInterruption();
        }

        long start = System.currentTimeMillis();
        long timeout = unit.toMillis(time);

        //判断 lock是否过期
        while (!useTimeout || isTimeout(start, timeout)) {
            if(tryLock()) {
                log.warn("得到锁：" + lockKey);
                return true;
            }
        }

        return false;
    }

    @Override
    public void unlock() {
        if (Thread.currentThread() != this.ownerThread) {
            return;
        }
        doUnlock();
        log.warn("释放锁：" + lockKey);
    }

    @Override
    public Condition newCondition() {
        return null;
    }


    protected void doUnlock() {
        //判断锁是否过期
        redisClientIF.releaseLock(lockKey);
        this.locked = false;
        this.ownerThread = null;
    }

    private void checkInterruption() throws InterruptedException {
        if (Thread.currentThread().isInterrupted()) {
            throw new InterruptedException();
        }
    }

    private boolean isTimeExpired(String value) {
        return Long.parseLong(value) < System.currentTimeMillis();
    }

    private boolean isTimeout(long start, long timeout) {
        return start + timeout > System.currentTimeMillis();
    }
}
