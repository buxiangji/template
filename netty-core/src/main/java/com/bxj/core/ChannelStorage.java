package com.bxj.core;

import io.netty.channel.Channel;
import io.netty.util.internal.StringUtil;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author buxiangji
 * @makedate 2023/7/7 15:24
 */
public class ChannelStorage {

    private static ChannelStorage channelStorage = null;

    public static ChannelStorage getInstance(){
        if(channelStorage == null){
            synchronized (ChannelStorage.class){
                if(channelStorage == null){
                    channelStorage = new ChannelStorage();
                }
            }
        }
        return channelStorage;
    }

    private ChannelStorage(){}

    private Map<String, UserInfo> userNameMap = new ConcurrentHashMap<>();

    private Map<String, UserInfo> userIPMap = new ConcurrentHashMap<>();

    public UserInfo getUserByName(String name){
        if(name == null){
            return null;
        }
        return userNameMap.get(name);
    }

    public UserInfo getUserById(String ipAdd){
        if(ipAdd == null){
            return null;
        }
        return userIPMap.get(ipAdd);
    }

    public boolean setUser(String name,String ip,Channel channel){
        if(StringUtil.isNullOrEmpty(name) && StringUtil.isNullOrEmpty(ip)){
            return false;
        }
        UserInfo userInfo = null;
        if(name != null && userNameMap.containsKey(name)){
            userInfo = userNameMap.get(name);
        }
        if(ip != null && userInfo == null){
            userInfo = userIPMap.get(ip);
        }
        if(userInfo == null){
            userInfo = new UserInfo();
        }
        userInfo.setIp(ip);
        userInfo.setName(name);
        userInfo.setCurrentChannel(channel);
        if(ip != null){
            userIPMap.put(ip,userInfo);
        }
        if(name != null){
            userNameMap.put(name, userInfo);
        }
        return true;
    }

    public static void main(String[] args) {
        String s = "/127.0.0.1:50202";
        String substring = s;
        System.out.println(substring);
        String[] split = substring.split(":");
    }
}
