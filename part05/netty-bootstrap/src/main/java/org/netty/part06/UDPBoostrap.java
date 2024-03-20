package org.netty.part06;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.oio.OioEventLoopGroup;
import io.netty.channel.socket.DatagramPacket;
import io.netty.channel.socket.oio.OioDatagramChannel;

import java.net.InetSocketAddress;

public class UDPBoostrap {

    public static void main(String[] args) {
        EventLoopGroup group = new OioEventLoopGroup();
        Bootstrap bootstrap = new Bootstrap();
        bootstrap.group(group)
                .channel(OioDatagramChannel.class)
                .handler(new SimpleChannelInboundHandler<DatagramPacket>() {
                    @Override
                    protected void channelRead0(ChannelHandlerContext ctx, DatagramPacket msg) throws Exception {
                        //Do something with the packet
                    }
                })
                .bind(new InetSocketAddress(0))
                .addListener((ChannelFutureListener) future -> {
                    if (future.isSuccess()){
                        System.out.println("Channel bound");
                    }else {
                        System.err.println("Bind attempt failed");
                        future.cause().printStackTrace();
                    }
                });
        group.shutdownGracefully().syncUninterruptibly();
    }

}
