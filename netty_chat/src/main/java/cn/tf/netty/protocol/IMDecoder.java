package cn.tf.netty.protocol;


import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import org.msgpack.MessagePack;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class IMDecoder  extends ByteToMessageDecoder {

    //解析IM写一下请求内容的正则
    private Pattern pattern = Pattern.compile("^\\[(.*)\\](\\s\\-\\s(.*))?");

    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
            final int length = in.readableBytes();
            final byte[] array = new byte[length];
            //把网络传输过来的内容，变成一个字符串
            String content = new String(array,in.readerIndex(),length);
            //空消息不解析
            if(!(null == content || "".equals(content.trim()))){
                if(!IMP.isIMP(content)){
                    ctx.channel().pipeline().remove(this);
                    return;
                }
            }
            //把字符串变成一个可以识别的IMMessage对象
            in.getBytes(in.readerIndex(), array, 0, length);
            //利用序列化协议，将网络流信息直接转化为IMMessage对象
            out.add(new MessagePack().read(array,IMMessage.class));
            in.clear();
    }

    public  IMMessage decode(String msg){
        if(null ==msg || "".equals(msg.trim())){return  null;}
        try{
            Matcher m = pattern.matcher(msg);
            String header = "";
            String content = "";
            if(m.matches()){
                header = m.group(1);
                content = m.group(3);
            }
            String[] headers = header.split("\\]\\[");
            long time = Long.parseLong(headers[1]);
            String nickName = headers[2];
            //昵称最多十个字
            nickName = nickName.length() < 10 ? nickName : nickName.substring(0, 9);

            if(msg.startsWith("[" + IMP.LOGIN.getName() + "]")){
                return new IMMessage(headers[0],headers[3],time,nickName);
            }else if(msg.startsWith("[" + IMP.CHAT.getName() + "]")){
                return new IMMessage(headers[0],time,nickName,content);
            }else if(msg.startsWith("[" + IMP.FLOWER.getName() + "]")){
                return new IMMessage(headers[0],headers[3],time,nickName);
            }else{
                return null;
            }
        }catch(Exception e){
            e.printStackTrace();
            return null;
        }
    }

}
