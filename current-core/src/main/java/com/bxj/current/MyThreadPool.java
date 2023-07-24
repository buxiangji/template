package com.bxj.current;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.function.Function;

public class MyThreadPool {
    private List<MyThread> threads = new CopyOnWriteArrayList<>();
    public MyThreadPool(int num){
        for(int i=0; i<num; i++){
            threads.add(new MyThread());
        }
    }

    public synchronized MyThread get(){
        boolean isRunning = true;
        MyThread first = null;
        int index = 0;
        while(isRunning){
            first = threads.get(index);
            if(!first.isRunning()){
                break;
            }
            index ++;
            if(index == threads.size()) {
                index = 0;
            }
        }
        return first;
    }

    public void excute(Function function, Object param){
        MyThread thread = this.get();
        System.out.println("获取到了线程"+thread.getName());
        thread.setFunction(function, param);
        thread.setRunning();
        synchronized (MyThread.lock){
            MyThread.lock.notifyAll();
        }
        if(thread.getState() == Thread.State.NEW) {
            thread.start();
        }
    }

    public void shutdown(){
        for (MyThread thread : threads) {
            thread.interrupt();
            thread=null;
        }
    }


    public static void main(String[] args) throws InterruptedException {
        MyThreadPool myThreadPool = new MyThreadPool(10);
        for (int i = 0; i < 100; i++) {
            myThreadPool.excute(new Function() {
                @Override
                public Object apply(Object o) {
                    System.out.println(Thread.currentThread().getName() + ": 发出了哈哈哈哈的笑声!");
                    return null;
                }
            }, null);
        }
        Thread.sleep(10000);
        for (int i = 0; i < 100; i++) {
            myThreadPool.excute(new Function() {
                @Override
                public Object apply(Object o) {
                    System.out.println(Thread.currentThread().getName() + ": 发出了哈哈哈哈的笑声!");
                    return null;
                }
            }, null);
        }
    }
}
