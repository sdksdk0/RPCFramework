package cn.tf.netty.registry;

import cn.tf.netty.protocol.InvokerProtocol;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

import java.io.File;
import java.lang.reflect.Method;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 *     //1、根据一个包名将所有符合条件的class全部扫描出来，放到一个容器中
 *     //2、给每一个对应的class设置一个唯一的名称，作为服务的名称
 *     //3、当有客户端连接过来之后，就会获取协议内容
 *     //4、要去注册好的容器中找到符合条件的服务
 *     //5、通过远程调用provider得到返回的结果并回复给客户端
 */
public class RegistryHandler extends ChannelInboundHandlerAdapter {

    private List<String> classNames = new ArrayList<>();
    private Map<String,Object> registryMap = new ConcurrentHashMap<>();


    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        Object result = new Object();
        InvokerProtocol request = (InvokerProtocol) msg;
        System.out.println("接受到的数据是:"+request);
        if(registryMap.containsKey(request.getClassName())){
            Object service = registryMap.get(request.getClassName());
            Method method = service.getClass().getMethod(request.getMethodName(),request.getParamters());
            result = method.invoke(service,request.getValues());
        }
        ctx.write(result);
        ctx.flush();
        ctx.close();
        System.out.println("服务端计算的结果是:"+result);

    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        super.exceptionCaught(ctx, cause);
    }


    public RegistryHandler(){
        scannerClass("cn.tf.netty.provider");
        doRegistry();
    }

    private void scannerClass(String packageName) {
        URL url = this.getClass().getClassLoader().getResource(packageName.replaceAll("\\.","/"));
        File classPath = new File(url.getFile());
        for (File file: classPath.listFiles()) {
            if(file.isDirectory()){
                scannerClass(packageName+"."+file.getName());
            }else{
                classNames.add(packageName+"."+file.getName().replace(".class",""));
            }
        }
    }

    private void doRegistry(){
        if(classNames.isEmpty()){return;}
        for(String className:classNames){

            try {
                Class<?> clazz = Class.forName(className);
                Class<?> i = clazz.getInterfaces()[0]; //接口名称作为服务名
                String serviceName = i.getName();
                registryMap.put(serviceName,clazz.newInstance());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }



}
