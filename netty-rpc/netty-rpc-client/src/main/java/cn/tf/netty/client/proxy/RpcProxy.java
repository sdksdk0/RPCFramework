package cn.tf.netty.client.proxy;

import cn.tf.netty.api.protocol.InvokerProtocol;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.handler.codec.LengthFieldPrepender;
import io.netty.handler.codec.serialization.ClassResolvers;
import io.netty.handler.codec.serialization.ObjectDecoder;
import io.netty.handler.codec.serialization.ObjectEncoder;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

public class RpcProxy {

    public static<T> T create(Class<?> clazz){
        MethodProxy proxy = new MethodProxy(clazz);
        Class<?>[] interfaces = clazz.isInterface()?new Class[]{clazz}:clazz.getInterfaces();

        T result = (T)Proxy.newProxyInstance(clazz.getClassLoader(),interfaces,proxy);
        return result;
    }


    private static class MethodProxy implements InvocationHandler{

        private Class<?> clazz;

        public MethodProxy(Class<?> clazz) {
            this.clazz = clazz;
        }


        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
            if(Object.class .equals(method.getDeclaringClass())){
                return method.invoke(this,args);
            }else{
                return rpcInvoke(proxy,method,args);
            }
        }

        private Object rpcInvoke(Object proxy, Method method, Object[] args) {

                //先要构造一个协议的内容
                InvokerProtocol msg = new InvokerProtocol();
                msg.setClassName(this.clazz.getName());
                msg.setMethodName(method.getName());
                msg.setParamters(method.getParameterTypes());
                msg.setValues(args);

                final RpcProxyHandler proxyHandler = new RpcProxyHandler();

                //发起网络请求
                EventLoopGroup  workGroup = new NioEventLoopGroup();
                Bootstrap client = new Bootstrap();

            try {
                client.group(workGroup)
                        .channel(NioSocketChannel.class)
                        .option(ChannelOption.TCP_NODELAY, true)
                        .handler(new ChannelInitializer<SocketChannel>() {

                            @Override
                            protected void initChannel(SocketChannel client) throws Exception {
                                ChannelPipeline pipeline = client.pipeline();
                                //自定义编码解码
                                pipeline.addLast(new LengthFieldBasedFrameDecoder(Integer.MAX_VALUE, 0, 4, 0, 4));
                                //自定义编码器
                                pipeline.addLast(new LengthFieldPrepender(4));
                                //实参处理
                                pipeline.addLast("encoder", new ObjectEncoder());
                                pipeline.addLast("decoder", new ObjectDecoder(Integer.MAX_VALUE, ClassResolvers.cacheDisabled(null)));

                                //执行自己的逻辑
                                pipeline.addLast(proxyHandler);
                            }
                        });
                ChannelFuture future = client.connect("localhost", 8080).sync();
                future.channel().writeAndFlush(msg).sync();
                future.channel().closeFuture().sync();
            }catch (Exception e){
                e.printStackTrace();
            }finally {
                workGroup.shutdownGracefully();
            }

            return proxyHandler.getResponse();

        }
    }

}
