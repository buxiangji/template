package com.bxj.core;


import io.netty.channel.Channel;

/**
 * @author buxiangji
 * @makedate 2023/7/7 15:31
 */
public class UserInfo {
    private String ip;
    private String name;
    private Channel currentChannel;

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Channel getCurrentChannel() {
        return currentChannel;
    }

    public void setCurrentChannel(Channel currentChannel) {
        this.currentChannel = currentChannel;
    }
}
