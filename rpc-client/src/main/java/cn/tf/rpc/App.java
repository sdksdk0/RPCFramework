package cn.tf.rpc;

import cn.tf.rpc.service.IHelloService;
import cn.tf.rpc.service.IOrderService;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class App {

    public static void main(String[] args) {
       /* RpcProxyClient rpcProxyClient = new RpcProxyClient();
        IHelloService iHelloService = rpcProxyClient.clientProxy(
                IHelloService.class,"localhost",8080
        );
        String result = iHelloService.sayHello("bbbb");
        System.out.println(result);*/

       ApplicationContext context = new AnnotationConfigApplicationContext(SpringConfig.class);
       RpcProxyClient rpcProxyClient = context.getBean(RpcProxyClient.class);
        IHelloService iHelloService = rpcProxyClient.clientProxy(
                IHelloService.class,"localhost",8080
        );
        String result = iHelloService.sayHello("bbbb");
        System.out.println(result);

        IOrderService orderService =rpcProxyClient.clientProxy(
                IOrderService.class,"localhost",8080
        );
        System.out.println(orderService.queryOrderNewTop());

    }
}
