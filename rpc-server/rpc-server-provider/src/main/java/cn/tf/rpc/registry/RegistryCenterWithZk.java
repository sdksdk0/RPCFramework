package cn.tf.rpc.registry;

import cn.tf.rpc.config.ZkConfig;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.zookeeper.CreateMode;

public class RegistryCenterWithZk implements IRegistryCenter {

    CuratorFramework curatorFramework =null;

    {
        //初始化zookeeper的连接， 会话超时时间是5s，衰减重试
        curatorFramework = CuratorFrameworkFactory.builder().
                connectString(ZkConfig.CONNECTION_STR).sessionTimeoutMs(5000).
                retryPolicy(new ExponentialBackoffRetry(1000, 3)).
                namespace("registry")
                .build();
        curatorFramework.start();
    }


    @Override
    public void registry(String serviceName, String serviceAddress) {

        String servicePath = "/"+serviceName;
        try {
            if (curatorFramework.checkExists().forPath(servicePath) == null) {
                curatorFramework.create().creatingParentContainersIfNeeded()
                        .withMode(CreateMode.PERSISTENT).forPath(servicePath);
            }
            String addressPath=servicePath+"/"+serviceAddress;
            curatorFramework.create().withMode(CreateMode.EPHEMERAL).forPath(addressPath);
            System.out.println(addressPath+"服务注册成功");
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
