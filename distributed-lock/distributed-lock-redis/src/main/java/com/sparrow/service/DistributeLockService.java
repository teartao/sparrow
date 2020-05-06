package com.sparrow.service;

import com.sparrow.components.DistributedLock;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service("com.sparrow.service.DistributeLockService")
public class DistributeLockService {

    @Resource(name = "com.sparrow.components.DistributedLock")
    private DistributedLock distributedLock;
    int n = 500;


    public void secKill() {
        // 返回锁的value值，供释放锁时候进行判断
        String identifier = distributedLock.lockWithTimeout("resource", 5000, 1000);
        System.out.println(Thread.currentThread().getName() + "获得了锁");
        System.out.println(--n);
        distributedLock.releaseLock("resource", identifier);
    }
}
