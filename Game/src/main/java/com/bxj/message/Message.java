package com.bxj.message;

import lombok.Data;

/**
 * @author buxiangji
 * @makedate 2023/7/19 10:32
 */
@Data
public class Message {
    private Object data;
    private String targetIp;
    private String targetMac;
    private String ip;
    private String mac;

    public String toString(){
        return "数据："+(data == null?"null":data.toString());
    }
}
