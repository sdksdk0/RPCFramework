package cn.tf.rpc;

public class App {

    public static void main(String[] args) {
        IHelloService helloService = new HelloServiceImpl();
        RpcProxyService proxyService = new RpcProxyService();
        proxyService.publisher(helloService,8080);
    }
}
