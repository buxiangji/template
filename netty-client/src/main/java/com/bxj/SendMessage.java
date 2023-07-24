package com.bxj;

import com.bxj.core.HappyChatEnum;
import com.bxj.core.MessageBody;
import com.bxj.jframe.ChatWindow;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;

import java.util.Scanner;

/**
 * @author buxiangji
 * @makedate 2023/7/7 15:29
 */
public class SendMessage {
    public static void send(String message, Channel channel, String myName, String frendName){
        MessageBody messageBody = new MessageBody();
        messageBody.setClientName(myName);
        messageBody.setMessage(message);
        messageBody.setToWhoName(frendName);
        channel.writeAndFlush(messageBody);
    }

    public static void upLine(Channel channel,String myName){
        MessageBody messageBody = new MessageBody();
        messageBody.setClientName(myName);
        messageBody.setMessage(HappyChatEnum.UPDATE_CLIENT);
        messageBody.setToWhoName("system");
        channel.writeAndFlush(messageBody);
    }
}
