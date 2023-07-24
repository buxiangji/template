package com.bxj.core;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufUtil;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;
import io.netty.handler.codec.MessageToMessageEncoder;

import java.nio.CharBuffer;
import java.nio.charset.StandardCharsets;
import java.util.List;

/**
 * @author buxiangji
 * @makedate 2023/7/7 15:50
 */
public class ChatMessageEncoder extends MessageToMessageEncoder<MessageBody> {

    @Override
    protected void encode(ChannelHandlerContext ctx, MessageBody msg, List<Object> out) throws Exception {
        if(msg == null){
            return;
        }
        out.add(ByteBufUtil.encodeString(ctx.alloc(), CharBuffer.wrap(msg.encode()), StandardCharsets.UTF_8));
    }
}
