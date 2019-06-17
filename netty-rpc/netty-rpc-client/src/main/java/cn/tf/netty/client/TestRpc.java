package cn.tf.netty.client;


import cn.tf.netty.api.IService.IRpcHelloService;
import cn.tf.netty.api.IService.IRpcService;
import cn.tf.netty.client.proxy.RpcProxy;

public class TestRpc {

    public static void main(String[] args) {

        //远程调用
        IRpcHelloService helloService = RpcProxy.create(IRpcHelloService.class);

        System.out.println(helloService.hello("nice"));
        IRpcService service = RpcProxy.create(IRpcService.class);
        System.out.println("8+2="+service.add(8,2));
        System.out.println("8-2="+service.sub(8,2));
        System.out.println("8*2="+service.mutiply(8,2));
        System.out.println("8/2="+service.divide(8,2));

    }

}
