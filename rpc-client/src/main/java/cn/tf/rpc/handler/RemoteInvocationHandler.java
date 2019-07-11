package cn.tf.rpc.handler;

import cn.tf.rpc.bean.RpcRequest;
import cn.tf.rpc.discovery.IServiceDiscovery;
import org.springframework.util.StringUtils;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

public class RemoteInvocationHandler implements InvocationHandler {

   private IServiceDiscovery serviceDiscovery;
   private String version;

    public RemoteInvocationHandler(IServiceDiscovery serviceDiscovery, String version) {
        this.serviceDiscovery = serviceDiscovery;
        this.version = version;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        RpcRequest rpcRequest = new RpcRequest();
        rpcRequest.setClassName(method.getDeclaringClass().getName());
        rpcRequest.setMethodName(method.getName());
        rpcRequest.setParameters(args);
        rpcRequest.setVersion(version);

        String serviceName = rpcRequest.getClassName();
        if(!StringUtils.isEmpty(version)){
            serviceName = serviceName+"&"+version;
        }
        String serviceAddress = serviceDiscovery.discovery(serviceName);

        RpcNetTransport netTransport = new RpcNetTransport(serviceAddress);
        Object result = netTransport.send(rpcRequest);
        return result;
    }
}
