package com;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.util.CharsetUtil;
import org.junit.Test;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;


public class IoTest {
    @Test
    public void testByteBuf() throws IOException {
        //写入消息体
        ByteBuf buf = Unpooled.buffer();
        buf.writeInt(4);
        buf.writeInt(1024);
        buf.writeByte((byte) 1);
        buf.writeByte((byte) 1);
        int idx=buf.writerIndex();
        buf.writeInt(0);
        int len=buf.writeCharSequence("我爱你中国 adsfjowi", CharsetUtil.UTF_8);
        System.out.println(len);
        buf.setInt(idx,len);
        //byteBuffer.put
        //读取消息头，因为写完后position已经到10了，所以需要先反转为0，再从头读取
        byte[] bytes = new byte[buf.readableBytes()];
        buf.readBytes(bytes);
        buf.resetReaderIndex();
        System.out.println(buf.writerIndex());
        System.out.println(buf.readableBytes());
        System.out.println(bytes.length);
        //
        System.out.println(buf.readInt());
        System.out.println(buf.readInt());
        System.out.println(buf.readByte());
        System.out.println(buf.readByte());
        int rlen=buf.readInt();
        System.out.println(buf.readCharSequence(rlen, CharsetUtil.UTF_8));
    }

    @Test
    public void testByteBuf2() throws IOException {
        int testtype=0;
        int max_count=10000*1000;
        long bytelen=0;
        if (testtype==0){
            for(int i=0;i<max_count;i++){
                //写入消息体
                ByteBuf buf = Unpooled.buffer(10);
                buf.writeInt(4);
                buf.writeInt(1024);
                buf.writeInt(4);
                buf.writeInt(1024);
                bytelen+=buf.readableBytes();
            }
        }

        if (testtype==1){
            ByteBuf buf = Unpooled.buffer(32);
            for(int i=0;i<max_count;i++){
                //写入消息体
                buf.writeInt(4);
                buf.writeInt(1024);
                buf.writeInt(4);
                buf.writeInt(1024);
                bytelen+=buf.readableBytes();
                buf.clear();
            }
            System.out.println("capacity:"+buf.capacity());
        }

        System.out.println("size:"+bytelen);



    }
}
