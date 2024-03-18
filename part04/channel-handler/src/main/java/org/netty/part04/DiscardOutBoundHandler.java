package org.netty.part04;

import io.netty.channel.*;
import io.netty.util.ReferenceCountUtil;

public class DiscardOutBoundHandler extends ChannelOutboundHandlerAdapter {

    {
        ChannelPipeline pipeline = null;

    }

    @Override
    public void write(ChannelHandlerContext ctx, Object msg, ChannelPromise promise) throws Exception {
        super.write(ctx, msg, promise);
        ReferenceCountUtil.release(msg);
        promise.setSuccess();
    }
}
