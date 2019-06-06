package cn.tf.rpc;

import cn.tf.rpc.annotation.RpcService;

@RpcService(IHelloService.class)
public class HelloServiceImpl implements IHelloService {

    @Override
    public String sayHello(String content) {
        System.out.println("request in:"+content);
        return "Say Hello:"+content;
    }

    @Override
    public String saveUser(User user) {
        System.out.println("request in:"+user);
        return "SUCESS";
    }
}
