package org.netty.bytebuf;

import io.netty.buffer.*;

import java.nio.ByteBuffer;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

public class ByteBufDemo01 {

    {
        //1.支撑数组
        ByteBuf byteBuf =  ByteBufAllocator.DEFAULT.buffer(10);;
        if (byteBuf.hasArray()){ //检查 ByteBuf 是否有一个支撑数组
            byte[] array = byteBuf.array(); //如果有，则获取对该数组的引用
            int offset = byteBuf.arrayOffset(); //计算第一个字节的偏移量。
            //使用数组、偏移量和长度作为参数调用你的方法
            int length = byteBuf.arrayOffset() + byteBuf.readerIndex();
            handleArray(array,offset,length);
        }
    }

    {
        //2.直接缓冲区
        ByteBuf directBuf =  ByteBufAllocator.DEFAULT.buffer(10);;
        if (!directBuf.hasArray()){ //检查 ByteBuf 是否由数组支撑。如果不是，则这是一个直接缓冲区
            int length = directBuf.readableBytes(); //获取可读字节数
            byte[] array = new byte[length]; //分配一个新的数组来保存具有该长度的字节数据
            directBuf.getBytes(directBuf.readerIndex(),array); //将字节复制到该数组
            handleArray(array,0,length); //使用数组、偏移量和长度作为参数调用你的方法
        }
    }

    {
        //3.复合缓冲区
        //3.1.JDK原生实现
        ByteBuffer header = ByteBuffer.allocate(10);
        ByteBuffer body = ByteBuffer.allocate(10);
        ByteBuffer message2 = ByteBuffer.allocate(header.remaining() + body.remaining());
        message2.put(header);
        message2.put(body);
        message2.flip();

        //3.2.使用netty的实现
        CompositeByteBuf messageBuf = Unpooled.compositeBuffer();
        ByteBuf headerBuf =  ByteBufAllocator.DEFAULT.buffer(10);;
        ByteBuf bodyBuff =  ByteBufAllocator.DEFAULT.buffer(10);;
        messageBuf.addComponents(headerBuf,bodyBuff);  //将 ByteBuf 实例追加到 CompositeByteBuf
        messageBuf.removeComponent(0);  //删除位于索引位置为 0（第一个组件）的 ByteBuf
        for (ByteBuf byteBuf : messageBuf) { //循环遍历所有的 ByteBuf 实例
            System.out.println(byteBuf.toString());
        }
    }


    {
        //读取ByteBuf的数据
        ByteBuf byteBuf = ByteBufAllocator.DEFAULT.buffer(10);
        while (byteBuf.isReadable()){
            System.out.println(byteBuf.readByte());
        }
    }

    {
        //对ByteBuf进行切片
        Charset UTF8 = StandardCharsets.UTF_8;
        //创建该 ByteBuf 从索引 0 开始到索引 15结束的分段的副本
        ByteBuf byteBuf = Unpooled.copiedBuffer("Netty in Action rocks!", UTF8);
        //对ByteBuf进行数据切片
        ByteBuf copy = byteBuf.copy(0, 15);
        System.out.println(copy.toString());
        byteBuf.setByte(0,'J');  //更新索引 0 处的字节
        assert byteBuf.getByte(0) != copy.getByte(0);  //将会成功，因为数据不是共享的
    }

    //处理
    private static void handleArray(byte[] array, int offset, int length) {


    }

    public static void main(String[] args) {
        new ByteBufDemo01();
    }

}
