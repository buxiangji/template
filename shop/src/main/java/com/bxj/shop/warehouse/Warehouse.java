package com.bxj.shop.warehouse;

import com.bxj.shop.wares.Wares;

import java.util.HashMap;
import java.util.Map;
import java.util.Queue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.atomic.AtomicLong;

public class Warehouse {
    private static final long capacity = 100;

    private static Warehouse warehouse = null;
    private Warehouse(){}

    public static Warehouse getWarehouse(){
        if(warehouse == null) {
            synchronized (Warehouse.class) {
                if (warehouse == null) {
                    warehouse = new Warehouse();
                }
            }
        }
        return warehouse;
    }

    private AtomicLong currentWareCount = new AtomicLong();
    private ConcurrentLinkedQueue<Wares> waresQueue = new ConcurrentLinkedQueue<>();

    public boolean addWare(Wares wares){
        if(currentWareCount.get() >= capacity){
            System.out.println("仓库满了！");
            return false;
        }
        currentWareCount.addAndGet(1l);
        return waresQueue.offer(wares);
    }

    public Wares getWare(){
        Wares poll = waresQueue.poll();
        if(poll== null) throw new RuntimeException("仓库商品已售空");
        return poll;
    }
}
