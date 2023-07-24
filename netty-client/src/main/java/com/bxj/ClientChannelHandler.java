package com.bxj;

import com.bxj.core.MessageBody;
import com.bxj.jframe.ChatWindow;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

import javax.swing.*;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author buxiangji
 * @makedate 2023/7/7 14:54
 */
public class ClientChannelHandler extends ChannelInboundHandlerAdapter {
    public static Map<Channel,ChatWindow> channelViews = new ConcurrentHashMap<>();
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        MessageBody messageBody = (MessageBody) msg;
        System.out.println(messageBody.getClientName()+": "+messageBody.getMessage());
        ChatWindow chatWindow = channelViews.get(ctx.channel());
        if(chatWindow != null){
            chatWindow.setReceiveMessage(messageBody.getClientName()+": "+messageBody.getMessage());
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }

    public static void setChannelViews(Channel channel, ChatWindow chatWindow){
        channelViews.put(channel,chatWindow);
    }
}
