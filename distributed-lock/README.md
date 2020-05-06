# distributed lock 
分布式锁3种实现及redisson(基于redis实现的分布式锁客户端)使用
https://blog.csdn.net/xlgen157387/article/details/79036337

## 三种基础实现方式优劣势：
DB：受制于数据库性能
ZK：满足C P ，不满足 A (分布式锁CAP自行百度)
Redis：redis多节点集群模式存在问题，需要完善锁算法(故障、转移、同步等)

建议使用：Redisson，较完善，基于juc的Lock实现，api丰富灵活