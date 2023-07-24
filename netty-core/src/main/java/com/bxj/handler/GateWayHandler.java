package com.bxj.handler;

import io.netty.buffer.ByteBuf;
import io.netty.channel.*;

import java.nio.ByteBuffer;

/**
 * @author buxiangji
 * @makedate 2023/7/6 14:57
 */
public class GateWayHandler extends ChannelInboundHandlerAdapter {
    @Override
    public void channelRead(ChannelHandlerContext channelHandlerContext, Object o) throws Exception {
        channelHandlerContext.fireChannelRead(o);
    }
}
