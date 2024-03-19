package org.netty.part06;

import io.netty.bootstrap.Bootstrap;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

import java.net.InetSocketAddress;

public class ServerBoostrap {

    public static void main(String[] args) {
        ServerBootstrap bootstrap = new ServerBootstrap();
        bootstrap.group(new NioEventLoopGroup(),new NioEventLoopGroup())
                .channel(NioServerSocketChannel.class)
                .childHandler(new SimpleChannelInboundHandler<ByteBuf>() {
                    ChannelFuture channelFuture;
                    @Override
                    public void channelActive(ChannelHandlerContext ctx) throws Exception {
                        super.channelActive(ctx);
                        Bootstrap newbootstrap = new Bootstrap();
                        channelFuture = newbootstrap.channel(NioSocketChannel.class)
                                .handler(new SimpleChannelInboundHandler<ByteBuf>() {
                                    @Override
                                    protected void channelRead0(ChannelHandlerContext ctx, ByteBuf msg) throws Exception {
                                        System.out.println("Received data!");
                                    }
                                })
                                .group(ctx.channel().eventLoop())
                                .connect(new InetSocketAddress("thrid-website",80));
                    }

                    @Override
                    protected void channelRead0(ChannelHandlerContext ctx, ByteBuf msg) throws Exception {
                        System.out.println("Received data!");
                        if (channelFuture.isDone()){
                            //do something with the data
                        }
                    }
                })
                .bind(new InetSocketAddress(8080))
                .addListener((ChannelFuture future) -> {
                   if (future.isSuccess()){
                       System.out.println("Server bound");
                   }else {
                       System.err.println("Bound attempt failed");
                       future.cause().printStackTrace();
                   }
                });

    }

}
