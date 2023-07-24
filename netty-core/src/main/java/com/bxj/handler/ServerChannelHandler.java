package com.bxj.handler;

import com.bxj.core.ChannelStorage;
import com.bxj.core.HappyChatEnum;
import com.bxj.core.MessageBody;
import com.bxj.core.UserInfo;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

/**
 * @author buxiangji
 * @makedate 2023/7/6 15:17
 */
public class ServerChannelHandler extends ChannelInboundHandlerAdapter {
    ChannelStorage channelStorage = ChannelStorage.getInstance();
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        Channel channel = ctx.channel();
        String ip = channel.remoteAddress().toString().substring(1);
        channelStorage.setUser(null,ip,channel);
        System.out.println("客户端"+ip+"上线了");
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("channelInactive");
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        MessageBody messageBody = (MessageBody) msg;
        //更新自身
        if(HappyChatEnum.UPDATE_CLIENT.equals(messageBody.getMessage())){
            boolean success = channelStorage.setUser(messageBody.getClientName(), ctx.channel().remoteAddress().toString().substring(1),ctx.channel());
            messageBody.setMessage(success?HappyChatEnum.UPDATE_CLIENT_SUCCESS:HappyChatEnum.UPDATE_CLIENT_FAIL);
            ctx.channel().writeAndFlush(messageBody);
            return;
        }
        //寻址目标
        UserInfo user = channelStorage.getUserByName(messageBody.getToWhoName());
        if(user == null || user.getCurrentChannel() == null
                || user.getCurrentChannel().isOpen() == false
                || user.getCurrentChannel().isActive() == false){
            MessageBody mb = new MessageBody();
            mb.setClientName("系统提示");
            mb.setMessage("对方不在线!");
            ctx.channel().writeAndFlush(mb);
        }else {
            Channel currentChannel = user.getCurrentChannel();
            currentChannel.writeAndFlush(messageBody);
        }
    }


    //异常触发
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }
}
