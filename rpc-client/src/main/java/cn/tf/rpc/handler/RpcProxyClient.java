package cn.tf.rpc.handler;

import cn.tf.rpc.discovery.IServiceDiscovery;
import cn.tf.rpc.discovery.ServiceDiscoveryWithZk;
import cn.tf.rpc.handler.RemoteInvocationHandler;

import java.lang.reflect.Proxy;

public class RpcProxyClient {

    private IServiceDiscovery serviceDiscovery = new ServiceDiscoveryWithZk();


    public <T> T clientProxy(final Class<T> intefaceCls,String version){
       return  (T) Proxy.newProxyInstance(intefaceCls.getClassLoader(),new Class<?>[]{intefaceCls},
               new  RemoteInvocationHandler(serviceDiscovery,version));
    }


}
