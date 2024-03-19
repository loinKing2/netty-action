package org.netty.part04;

import io.netty.channel.*;

import java.util.concurrent.Future;

public class OutboundExceptionHandler extends ChannelOutboundHandlerAdapter {

    @Override
    public void write(ChannelHandlerContext ctx, Object msg, ChannelPromise promise) throws Exception {
        super.write(ctx, msg, promise);
        promise.addListener((ChannelFutureListener) future -> {
            if (!future.isSuccess()){
                future.cause().printStackTrace();
                future.channel().close();
            }
        });
    }
}
