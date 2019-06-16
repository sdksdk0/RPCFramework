package cn.tf.netty.consumer;

import cn.tf.netty.api.IRpcHelloService;
import cn.tf.netty.api.IRpcService;
import cn.tf.netty.consumer.proxy.RpcProxy;
import cn.tf.netty.provider.RpcHelloServiceImpl;
import cn.tf.netty.provider.RpcServiceImpl;

public class RpcConsumer {


    public static void main(String[] args) {
        //本地方法调用
      /*  IRpcHelloService helloService = new RpcHelloServiceImpl();
        System.out.println(helloService.hello("nice"));

        IRpcService service = new RpcServiceImpl();
        System.out.println("8+2="+service.add(8,2));
        System.out.println("8-2="+service.sub(8,2));
        System.out.println("8*2="+service.mutiply(8,2));
        System.out.println("8/2="+service.divide(8,2));*/

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
