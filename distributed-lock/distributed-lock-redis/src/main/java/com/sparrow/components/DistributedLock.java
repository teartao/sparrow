package com.sparrow.components;

import org.springframework.stereotype.Component;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.Transaction;
import redis.clients.jedis.exceptions.JedisException;

import javax.annotation.Resource;
import java.util.List;
import java.util.UUID;

/**
 * 分布式锁的简单实现代码
 */

@Component("com.sparrow.components.DistributedLock")
public class DistributedLock {

    @Resource(name = "jedisPool")
    private JedisPool jedisPool;

    /**
     * 加锁
     *
     * @param lockName       锁的key
     * @param acquireTimeout 获取超时时间
     * @param timeout        锁的超时时间
     * @return 锁标识
     */
    public String lockWithTimeout(String lockName, long acquireTimeout, long timeout) {
        try (Jedis conn = jedisPool.getResource()) {
            // 获取连接
            // 随机生成一个value,用于后续释放锁时判断，不一致则不释放，避免释放redis中其它线程的锁
            String identifier = UUID.randomUUID().toString();
            // 锁名，即key值，根据业务需要取名
            String lockKey = "lock:" + lockName;
            // 超时时间，上锁后超过此时间则自动释放锁，避免长时间占用锁，造成死锁，影响系统性能
            int lockExpire = (int) (timeout / 1000);

            // 获取锁的超时时间，超过这个时间则放弃获取锁，说明被其它资源占用，不再继续获取锁
            long end = System.currentTimeMillis() + acquireTimeout;
            while (System.currentTimeMillis() < end) {
                //原子操作(核心)。向redis写入锁，写入成功则返回1，否则写入失败，说明获取锁失败
                if (conn.setnx(lockKey, identifier) == 1) {
                    conn.expire(lockKey, lockExpire);
                    // 返回value值，用于释放锁时间确认
                    return identifier;
                }
                // 返回-1代表key没有设置超时时间，为key设置一个超时时间
                if (conn.ttl(lockKey) == -1) {
                    conn.expire(lockKey, lockExpire);
                }

                //短时暂停线程，避免集中竞争锁
                try {
                    Thread.sleep(10);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
        } catch (JedisException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 释放锁
     *
     * @param lockName   锁的key
     * @param identifier 释放锁的标识
     * @return
     */
    public boolean releaseLock(String lockName, String identifier) {
        Jedis conn = null;
        String lockKey = "lock:" + lockName;
        boolean retFlag = false;
        try {
            conn = jedisPool.getResource();
            while (true) {
                // 监视lock，准备开始redis事务，在multi之前使用watch监听键值变化
                conn.watch(lockKey);
                // 通过前面返回的value值判断是不是该锁，若是该锁，则删除，释放锁
                if (identifier.equals(conn.get(lockKey))) {
                    Transaction transaction = conn.multi();
                    transaction.del(lockKey);
                    List<Object> results = transaction.exec();
                    if (results == null) {
                        continue;
                    }
                    retFlag = true;
                }
                conn.unwatch();
                break;
            }
        } catch (JedisException e) {
            e.printStackTrace();
        } finally {
            if (conn != null) {
                conn.close();
            }
        }
        return retFlag;
    }
}
