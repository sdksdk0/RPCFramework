package cn.tf.rpc;

import cn.tf.rpc.RemoteInvocationHandler;

import java.lang.reflect.Proxy;

public class RpcProxyClient {

    public <T> T clientProxy(final Class<T> intefaceCls,final String host,final int port){
       return  (T) Proxy.newProxyInstance(intefaceCls.getClassLoader(),new Class<?>[]{intefaceCls},new  RemoteInvocationHandler(host,port));
    }


}
