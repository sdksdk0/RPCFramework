package cn.tf.rpc;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class App {

    public static void main(String[] args) {
       /* IHelloService helloService = new HelloServiceImpl();
        RpcProxyService proxyService = new RpcProxyService();
        proxyService.publisher(helloService,8080);*/

        ApplicationContext context = new AnnotationConfigApplicationContext(SpringConfig.class);
        ((AnnotationConfigApplicationContext) context).start();



    }
}
