package cn.tf.netty;

import cn.tf.netty.registry.RpcRegistry;

public class Application {


    public static void main(String[] args) {
        new RpcRegistry(8080).start();
    }

}
