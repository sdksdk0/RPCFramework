package cn.tf.rpc.annotation;

import org.springframework.stereotype.Component;

import java.lang.annotation.ElementType;
import java.lang.annotation.Target;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;


@Target(ElementType.TYPE) //修饰类或者接口
@Retention(RetentionPolicy.RUNTIME)
@Component //被spring进行扫描
public @interface RpcService {

    Class<?> value(); //拿到服务的接口

/*    *//**
     * 版本号
     *//*
    String version() default "";*/

}
