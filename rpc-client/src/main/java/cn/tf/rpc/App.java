package cn.tf.rpc;

public class App {

    public static void main(String[] args) {
        RpcProxyClient rpcProxyClient = new RpcProxyClient();
        IHelloService iHelloService = rpcProxyClient.clientProxy(
                IHelloService.class,"localhost",8080
        );
        String result = iHelloService.sayHello("bbbb");
        System.out.println(result);
    }
}
