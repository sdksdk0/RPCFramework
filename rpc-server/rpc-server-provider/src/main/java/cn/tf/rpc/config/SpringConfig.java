package cn.tf.rpc.config;

import cn.tf.rpc.handle.TFRpcServer;
import cn.tf.rpc.registry.RegistryCenterWithZk;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(basePackages = "cn.tf.rpc")
public class SpringConfig {

    @Bean(name = "tFRpcServer")
    public TFRpcServer tFRpcServer(){
        return new TFRpcServer(8084,new RegistryCenterWithZk());
    }

}
