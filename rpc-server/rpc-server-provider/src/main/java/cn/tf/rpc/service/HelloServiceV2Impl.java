package cn.tf.rpc.service;

import cn.tf.rpc.bean.User;
import cn.tf.rpc.annotation.RpcService;

@RpcService(value = IHelloService.class,version = "2.0")
public class HelloServiceV2Impl implements IHelloService {

    @Override
    public String sayHello(String content) {
        System.out.println("[V2.0]request in:"+content);
        return "[V2.0]Say Hello:"+content;
    }

    @Override
    public String saveUser(User user) {
        System.out.println("[V2.0]request in:"+user);
        return "SUCESS";
    }
}
