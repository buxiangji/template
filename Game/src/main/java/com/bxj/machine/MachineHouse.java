package com.bxj.machine;

import com.bxj.device.Device;
import com.bxj.device.PC;
import com.bxj.device.Router;
import com.bxj.util.IPUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * @author buxiangji
 * @makedate 2023/7/21 17:27
 */
public abstract class MachineHouse {

    public  List<Router> Routers = new ArrayList<>();
    private static List<PC> list = new ArrayList<>();

    public abstract void firePC();
    public abstract void fireRoute();
    public abstract void fireDevice();

    public abstract void rebootPC();
    public abstract void rebootRoute();
    public abstract void rebootDevice();

    public abstract void shutdownPC();
    public abstract void shutdownRouter();
    public abstract void shutdownDevice();

    public static void initDevice(){
        List<Device> list1 = new ArrayList<>();
        for(int i=10; i<110; i++){
            PC pc = new PC();
            pc.setIp("3.0.0."+i);
            pc.setMask("255.0.0.0");
            pc.setMac(UUID.randomUUID().toString());
            list1.add(pc);
        }
        IPUtil.initDevice(list1,"london","pc");
        list1.clear();
        for(int i=8; i<10; i++){
            Router pc = new Router();
            pc.setIp("3.0.0."+i);
            pc.setMask("255.0.0.0");
            pc.setMac(UUID.randomUUID().toString());
            list1.add(pc);
        }
        IPUtil.initDevice(list1,"london","Routerr");
    }

    public static void main(String[] args) {
        initDevice();
    }
}
