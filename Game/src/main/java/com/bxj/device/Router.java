package com.bxj.device;

import com.bxj.message.Message;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author buxiangji
 * @makedate 2023/7/19 10:23
 */
@Data
public class Router extends Device{

    private String gateway;
    private String dns;
    private Map<String,String> ip2Mac = new HashMap<>();
    @JsonIgnore
    private List<Router> routes = new ArrayList<>();
    @JsonIgnore
    private Map<String,PC> pcMap = new HashMap<>();

    private PC getPC(String mac){
        return pcMap.get(mac);
    }

    public void addPC(PC pc){
        ip2Mac.put(pc.getIp(),pc.getMac());
        pcMap.put(pc.getMac(), pc);
    }

    public void addPC(List<PC> pcs){
        for (PC pc : pcs) {
            ip2Mac.put(pc.getIp(),pc.getMac());
            pcMap.put(pc.getMac(), pc);
        }
    }

    public PC getPC(Message message){
        PC pc = getPC(ip2Mac.get(message.getTargetIp()));
        //如果为空，转发给连接路由
        if(pc == null){
            for (Router route : routes) {
                pc = route.getPC(message);
                if (pc != null)
                return pc;
            }
        }
        return pc;
    }

    public void setRoute(Router route){
        routes.add(route);
    }


    @Override
    public String toString() {
        return "Route{" +
                "mac='" + getMac() + '\'' +
                ", ip='" + getIp() + '\'' +
                ", mask='" + getMask() + '\'' +
                ", dns='" + getDns() + '\'' +
                '}';
    }
}
