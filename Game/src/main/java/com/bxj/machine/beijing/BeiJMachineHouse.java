package com.bxj.machine.beijing;

import com.bxj.device.PC;
import com.bxj.device.Router;
import com.bxj.machine.MachineHouse;
import com.bxj.util.IPUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * @author buxiangji
 * @makedate 2023/7/20 10:03
 */
public class BeiJMachineHouse extends MachineHouse {
    private static final String rootIp = "1.0.0.0";
    private static final String mask = "255.0.0.0";
    
    private List<PC> pcs = new ArrayList<>();
    private List<Router> routes = new ArrayList<>();

    private static final BeiJMachineHouse beiJMachineHouse = new BeiJMachineHouse();
    private BeiJMachineHouse(){
        firePC();
        fireRoute();
        for (Router route : routes) {
            route.addPC(pcs);
        }
        for (PC pc : pcs) {
            pc.setRoutes(routes);
        }
    }

    public static BeiJMachineHouse getMHouse(){
        return beiJMachineHouse;
    }
    
    @Override
    public void firePC() {
        pcs = IPUtil.read("beijing", "pc", PC.class);
    }

    @Override
    public void fireRoute() {
        routes = IPUtil.read("beijing", "router", Router.class);
    }

    @Override
    public void fireDevice() {

    }

    @Override
    public void rebootPC() {

    }

    @Override
    public void rebootRoute() {

    }

    @Override
    public void rebootDevice() {

    }

    @Override
    public void shutdownPC() {

    }

    @Override
    public void shutdownRouter() {

    }

    @Override
    public void shutdownDevice() {

    }

    public static void main(String[] args) {
        BeiJMachineHouse mHouse = BeiJMachineHouse.getMHouse();
        List<PC> pcs1 = mHouse.pcs;
        PC pc = pcs1.get(0);
        boolean ping = pc.ping("1.0.0.18");
        System.out.println(ping);
    }
}
