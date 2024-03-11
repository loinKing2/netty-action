package org.netty.server;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import org.netty.handle.EchoServerHandler;

import java.net.InetSocketAddress;

public class EchoServer {
    private final int port;
    public EchoServer(int port) {
        this.port = port;
    }

    public static void main(String[] args) throws Exception {
        if (args.length != 1) {
            /*
            设置端口值（如果端口参数的格式不正确，则抛出一个
             */
            System.err.println("Usage: " + EchoServer.class.getSimpleName() + "<port>");
        }
        int port = Integer.parseInt(args[0]);
        new EchoServer(port).start(); //调用服务器的 start()方法
    }

    private void start() throws Exception {
        final EchoServerHandler handler = new EchoServerHandler();
        EventLoopGroup group = new NioEventLoopGroup();  //1.创建Event-LoopGroup
        try {
            ServerBootstrap b = new ServerBootstrap();  //2.创建Server-Bootstrap
            b.group(group)
                    .channel(NioServerSocketChannel.class)  //3.指定所使用的 NIO传输 Channel
                    .localAddress(new InetSocketAddress(port)) //4.使用指定的端口设置套接字地址
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        protected void initChannel(SocketChannel socketChannel) throws Exception {
                            socketChannel.pipeline().addLast(handler); //添加一个EchoServer-Handler 到子Channel的 ChannelPipeline
                        }
                    });
            ChannelFuture f = b.bind().sync(); //异步地绑定服务器；调用 sync()方法阻塞等待直到绑定完成
            f.channel().closeFuture().sync(); //获取 Channel 的CloseFuture，并且阻塞当前线程直到它完成
        }finally {
            group.shutdownGracefully().sync();
        }


    }

}
