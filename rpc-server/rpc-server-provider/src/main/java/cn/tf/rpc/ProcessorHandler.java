package cn.tf.rpc;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.Socket;
import java.util.Map;

public class ProcessorHandler implements Runnable {

    private Socket socket;
    private Map<String,Object> handleMap;

    public ProcessorHandler(Socket socket, Map<String,Object> handleMap) {
        this.socket = socket;
        this.handleMap = handleMap;
    }

    @Override
    public void run() {
        ObjectInputStream ois = null;
        ObjectOutputStream oos = null;

        try {
            ois = new ObjectInputStream(socket.getInputStream());
            RpcRequest rpcRequest = (RpcRequest) ois.readObject();
            Object result = invoke(rpcRequest);
            oos = new ObjectOutputStream(socket.getOutputStream());
            oos.writeObject(result);
            oos.flush();

        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            if(ois!=null){
                try {
                    ois.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if(oos!=null){
                try {
                    oos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private Object invoke(RpcRequest request) throws ClassNotFoundException, NoSuchMethodException, InvocationTargetException, IllegalAccessException {

        String serviceName = request.getClassName();

       Object service =  handleMap.get(serviceName);
        if(null == service){
            throw  new RuntimeException("service not found:"+service);
        }
        //反射调用
        Object[] args = request.getParameters();
        Class<?>[] types = new Class[args.length];
        for (int i = 0; i < args.length; i++) {
            types[i] = args[i].getClass();
        }

        Class clazz = Class.forName(request.getClassName());
        Method method = clazz.getMethod(request.getMethodName(), types);
        return method.invoke(service,args);

    }
}
