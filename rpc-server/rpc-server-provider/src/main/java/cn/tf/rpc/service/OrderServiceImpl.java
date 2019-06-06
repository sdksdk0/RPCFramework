package cn.tf.rpc.service;

import cn.tf.rpc.annotation.RpcService;

import java.util.Date;

@RpcService(value=IOrderService.class,version = "1.0")
public class OrderServiceImpl implements IOrderService{


    @Override
    public String queryOrderNewTop() {
        return "1000000";
    }
}
