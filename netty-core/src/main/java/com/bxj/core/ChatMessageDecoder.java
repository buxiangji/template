package com.bxj.core;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageDecoder;

import java.nio.charset.StandardCharsets;
import java.util.List;

/**
 * @author buxiangji
 * @makedate 2023/7/7 15:51
 */
public class ChatMessageDecoder extends MessageToMessageDecoder<ByteBuf> {

    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf msg, List<Object> out) throws Exception {
        if(msg == null){
            return;
        }
        MessageBody decode = MessageBody.decode(msg.toString(StandardCharsets.UTF_8));
        out.add(decode);
    }
}
