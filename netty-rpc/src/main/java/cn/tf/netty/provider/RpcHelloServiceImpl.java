package cn.tf.netty.provider;

import cn.tf.netty.api.IRpcHelloService;

public class RpcHelloServiceImpl implements IRpcHelloService {
    public String hello(String name) {
        return "Hello! "+name;
    }
}
