# distributed-lock-redis
redis版本分布式锁实现

> Intellij IDEA 可以通过 Settings -> File Encodings中
> 勾选 'transparent native-to-ascii conversion' 来显示properties中Unicode中文字符

# 解释
核心代码主要2块:
获取锁：`com.sparrow.components.DistributedLock.lockWithTimeout`
释放锁：`com.sparrow.components.DistributedLock.releaseLock`


