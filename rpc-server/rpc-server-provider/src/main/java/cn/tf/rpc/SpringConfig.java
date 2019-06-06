package cn.tf.rpc;

import cn.tf.rpc.handle.TFRpcServer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(basePackages = "cn.tf.rpc")
public class SpringConfig {

    @Bean(name = "tFRpcServer")
    public TFRpcServer tFRpcServer(){
        return new TFRpcServer(8080);
    }

}
