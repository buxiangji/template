package com.bxj.device;

import com.bxj.message.Message;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import java.util.List;

/**
 * @author buxiangji
 * @makedate 2023/7/19 10:22
 */
@Data
public class PC extends Device{
    private String gateway;
    private String dns;
    @JsonIgnore
    private List<Router> routes;

    public boolean ping(String ip){
        Message message = new Message();
        message.setTargetIp(ip);
        return send(message) == 200;
    }

    public int send(Message message){
        for (Router route : routes) {
            PC pcByIP = route.getPC(message);
            if(pcByIP == null){
                return 404;
            }else {
                pcByIP.receive(message);
                return 200;
            }
        }
        return 404;
    }

    private void receive(Message message){
        System.out.println(message.toString());
    }

    @Override
    public String toString() {
        return "PC{" +
                "mac='" + getMac() + '\'' +
                ", ip='" + getIp() + '\'' +
                ", mask='" + getMask() + '\'' +
                ", dns='" + dns + '\'' +
                '}';
    }
}
