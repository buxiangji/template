package com.bxj.current;

import java.util.function.Function;

public class MyThread extends Thread{
    public static final Object lock = new Object();
    private Function function;
    private Object param;

    private boolean isRunning = false;

    void setFunction(Function function, Object param){
        this.function = function;
        this.param = param;
    }

    @Override
    public void run() {
        while(true) {
            if(function != null) {
                isRunning = true;
                try {
                    Object apply = function.apply(param);
                }catch (Exception e){
                    e.printStackTrace();
                }finally {
                    isRunning = false;
                    param = null;
                    function = null;
                }
            }
            synchronized (lock){
                try {
                    lock.wait();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }

    public void setRunning(){
        isRunning =true;
    }

    public boolean isRunning(){
        return isRunning;
    }
}
