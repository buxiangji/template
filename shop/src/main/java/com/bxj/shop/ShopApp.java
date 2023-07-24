package com.bxj.shop;

import com.bxj.current.MyThreadPool;
import com.bxj.shop.warehouse.Warehouse;
import com.bxj.shop.wares.Wares;

import java.util.List;
import java.util.UUID;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.function.Function;

public class ShopApp {

    public static void main(String[] args) throws InterruptedException {
        Warehouse warehouse = Warehouse.getWarehouse();
        for (int i = 0; i < 101; i++) {
            Wares wares = new Wares();
            wares.setUuid(UUID.randomUUID().toString());
            wares.setName("iPhone14");
            wares.setPrice(500.00);
            wares.setWeight(180);
            warehouse.addWare(wares);
        }
        List<Wares> list = new CopyOnWriteArrayList<>();
        MyThreadPool myThreadPool = new MyThreadPool(10);
        for (int i = 0; i < 101; i++) {
            myThreadPool.excute(new Function() {
                @Override
                public Object apply(Object o) {
                    list.add(warehouse.getWare());
                    return null;
                }
            }, null);
        }
        Thread.sleep(1000);
        System.out.println(list.size());
    }
}
