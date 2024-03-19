package org.netty.part04;

import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

/**
 * 处理入站事件异常信息
 */
public class InBoundExceptionHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        super.exceptionCaught(ctx, cause);
        cause.printStackTrace();
        ctx.close();
    }




    {

        ChannelFuture future = null;
        future.addListener((ChannelFutureListener) future1 -> {
            if (!future1.isSuccess()) {
                future1.cause().printStackTrace();
                future1.channel().close();
            }
        });
    }

}
