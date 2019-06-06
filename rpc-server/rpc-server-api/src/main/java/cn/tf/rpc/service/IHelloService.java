package cn.tf.rpc.service;

import cn.tf.rpc.bean.User;

public interface IHelloService {

    String sayHello(String content);

    String saveUser(User user);

}
