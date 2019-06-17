package cn.tf.netty.service;

import cn.tf.netty.api.IService.IRpcHelloService;

public class RpcHelloServiceImpl implements IRpcHelloService {
    public String hello(String name) {
        return "Hello! "+name;
    }
}
