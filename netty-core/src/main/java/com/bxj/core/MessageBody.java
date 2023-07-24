package com.bxj.core;

import io.netty.util.internal.StringUtil;

import java.io.Serializable;

/**
 * @author buxiangji
 * @makedate 2023/7/7 15:44
 */
public class MessageBody implements Serializable {
    private static final String CONCAT = "#@#";
    private static final String NULL = "NULL";
    private String clientName;
    private String message;
    private String toWhoName;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getClientName() {
        return clientName;
    }

    public void setClientName(String clientName) {
        this.clientName = clientName;
    }

    public String getToWhoName() {
        return toWhoName;
    }

    public void setToWhoName(String toWhoName) {
        this.toWhoName = toWhoName;
    }

    public String encode(){
        return (StringUtil.isNullOrEmpty(this.clientName)?NULL:this.clientName)
                +CONCAT+
                (StringUtil.isNullOrEmpty(this.message)?NULL:this.message)
                +CONCAT+
                (StringUtil.isNullOrEmpty(this.toWhoName)?NULL:this.toWhoName);
    }

    public static MessageBody decode(String s){
        if(StringUtil.isNullOrEmpty(s)){
            return new MessageBody();
        }
        String[] split = s.split(CONCAT);
        MessageBody messageBody = new MessageBody();
        if(!NULL.equals(split[0]))
        messageBody.setClientName(split[0]);
        if(!NULL.equals(split[1]))
        messageBody.setMessage(split[1]);
        if(!NULL.equals(split[2]))
        messageBody.setToWhoName(split[2]);
        return messageBody;
    }

    @Override
    public String toString() {
        return "MessageBody{" +
                "clientName='" + clientName + '\'' +
                ", message='" + message + '\'' +
                ", toWhoName='" + toWhoName + '\'' +
                '}';
    }
}
