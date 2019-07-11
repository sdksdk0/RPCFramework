package cn.tf.rpc;

import cn.tf.rpc.config.SpringConfig;
import cn.tf.rpc.handler.RpcProxyClient;
import cn.tf.rpc.service.IHelloService;
import cn.tf.rpc.service.IOrderService;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class App {

    public static void main(String[] args) throws InterruptedException {
       /* RpcProxyClient rpcProxyClient = new RpcProxyClient();
        IHelloService iHelloService = rpcProxyClient.clientProxy(
                IHelloService.class,"localhost",8080
        );
        String result = iHelloService.sayHello("bbbb");
        System.out.println(result);*/

       ApplicationContext context = new AnnotationConfigApplicationContext(SpringConfig.class);
       RpcProxyClient rpcProxyClient = context.getBean(RpcProxyClient.class);
        IHelloService iHelloService = rpcProxyClient.clientProxy(
                IHelloService.class,"2.0"
        );
        //当服务端启动多个节点时，测试负载均衡的效果
        for(int i=0;i<10;i++){
            Thread.sleep(2000);
            String result = iHelloService.sayHello("bbbb");
            System.out.println(result);
        }

        IOrderService orderService =rpcProxyClient.clientProxy(
                IOrderService.class,"1.0"
        );
        System.out.println(orderService.queryOrderNewTop());

    }
}
