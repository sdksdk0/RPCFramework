package cn.tf.rpc.handler;

import cn.tf.rpc.bean.RpcRequest;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

/**
 * 网络传输处理类
 */
public class RpcNetTransport {

    private String serviceAddress;

    public RpcNetTransport(String serviceAddress) {
        this.serviceAddress = serviceAddress;
    }

    public Object send(RpcRequest request){
        Socket socket = null;
        Object result = null;
        ObjectOutputStream outputStream = null;
        ObjectInputStream inputStream = null;

        try {

            String urls[]=serviceAddress.split(":");
            socket = new Socket(urls[0],Integer.parseInt(urls[1]));

            outputStream = new ObjectOutputStream(socket.getOutputStream());
            outputStream.writeObject(request);
            outputStream.flush();

            inputStream = new ObjectInputStream(socket.getInputStream());
            result = inputStream.readObject();

        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }finally {
            if(inputStream!=null){
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if(outputStream!=null){
                try {
                    outputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return result;


    }


}
