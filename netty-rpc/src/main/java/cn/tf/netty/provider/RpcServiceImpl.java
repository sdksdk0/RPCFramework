package cn.tf.netty.provider;

import cn.tf.netty.api.IRpcService;

public class RpcServiceImpl implements IRpcService {
    public int add(int a, int b) {
        return a+b;
    }

    public int sub(int a, int b) {
        return a-b;
    }

    public int mutiply(int a, int b) {
        return a*b;
    }

    public int divide(int a, int b) {
        return b==0?0:a/b;
    }
}
