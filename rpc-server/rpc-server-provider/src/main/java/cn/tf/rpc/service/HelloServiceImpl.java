package cn.tf.rpc.service;

import cn.tf.rpc.bean.User;
import cn.tf.rpc.annotation.RpcService;

@RpcService(value = IHelloService.class,version = "1.0")
public class HelloServiceImpl implements IHelloService {

    @Override
    public String sayHello(String content) {
        System.out.println("[V1.0]request in:"+content);
        return "[V1.0]Say Hello:"+content;
    }

    @Override
    public String saveUser(User user) {
        System.out.println("[V1.0]request in:"+user);
        return "SUCESS";
    }
}
