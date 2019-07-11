package cn.tf.rpc.handle;

import cn.tf.rpc.annotation.RpcService;
import cn.tf.rpc.registry.IRegistryCenter;
import cn.tf.rpc.registry.RegistryCenterWithZk;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Component("tFRpcServer")
public class TFRpcServer implements ApplicationContextAware,InitializingBean {


    ExecutorService executorService = Executors.newCachedThreadPool();

    private Map<String,Object> handleMap = new HashMap<>();
    private int port;
    private IRegistryCenter iRegistryCenter =null;

    public TFRpcServer(int port, IRegistryCenter iRegistryCenter) {
        this.port = port;
        this.iRegistryCenter = iRegistryCenter;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        ServerSocket serverSocket = null;

        try {
            serverSocket = new ServerSocket(port);
            System.out.println("服务启动成功！");
            while(true){
                Socket socket = serverSocket.accept();
                executorService.execute(new ProcessorHandler(socket,handleMap));
            }

        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            if(serverSocket!=null){
                try {
                    serverSocket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {

        Map<String,Object> serviceBeanMap = applicationContext.getBeansWithAnnotation(RpcService.class);
        if(!serviceBeanMap.isEmpty()){
            for (Object serviceBean : serviceBeanMap.values()){
                //拿到注解
                RpcService rpcService = serviceBean.getClass().getAnnotation(RpcService.class);
                String serviceName = rpcService.value().getName(); //接口类定义
                String version = rpcService.version(); //版本号
                if(!StringUtils.isEmpty(version)){
                    serviceName+="&"+version;
                }
                handleMap.put(serviceName,serviceBean);
                iRegistryCenter.registry(serviceName,getAddress()+":"+port);
            }
        }
    }

    private static String getAddress(){
        InetAddress inetAddress = null;
        try {
            inetAddress = InetAddress.getLocalHost();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
        return inetAddress.getHostAddress();
    }


}
