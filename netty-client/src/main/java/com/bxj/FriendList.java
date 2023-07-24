package com.bxj;

import com.bxj.core.UserInfo;
import io.netty.channel.Channel;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author buxiangji
 * @makedate 2023/7/10 8:50
 */
public class FriendList {
    private static Map<String, UserInfo> friends = new ConcurrentHashMap<>();

    private static Set<String> friendNames(){
        return friends.keySet();
    }

    private static UserInfo getFriend(String userName){
        return friends.get(userName);
    }

    private static void setFriend(String name, String ip, Channel channel){
        UserInfo userInfo = null;
        if(friends.containsKey(name)){
            userInfo = friends.get(name);
        }else{
            userInfo = new UserInfo();
            friends.put(name, userInfo);
        }
        userInfo.setIp(ip);
        userInfo.setName(name);
        userInfo.setCurrentChannel(channel);
    }
}
