package com.bxj.util;

import com.bxj.device.Device;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @author buxiangji
 * @makedate 2023/7/19 10:49
 */
public class IPUtil {

    public static final ObjectMapper objectMapper = new ObjectMapper();
    public static boolean isLocalNet(String ip1,String ip2, String mask){
        char[] chars = ip1.toCharArray();
        char[] chars1 = mask.toCharArray();
        String complement = "";
        for (int i = 0; i < chars.length; i++) {
            int i1 = chars[i]-48 & chars1[i]-48;
            complement+=i1;
        }

        char[] chars2 = ip2.toCharArray();
        String complement1 = "";
        for (int i = 0; i < chars2.length; i++) {
            int i1 = chars2[i]-48 & chars1[i]-48;
            complement1+=i1;
        }
        return complement.equals(complement1);
    }

    public static String parseIpString(String ip){
        String[] ipSplit = ip.split("\\.");
        String s0 = fill(Integer.toBinaryString(Integer.parseInt(ipSplit[0])));
        String s1 = fill(Integer.toBinaryString(Integer.parseInt(ipSplit[1])));
        String s2 = fill(Integer.toBinaryString(Integer.parseInt(ipSplit[2])));
        String s3 = fill(Integer.toBinaryString(Integer.parseInt(ipSplit[3])));
        return s0+"."+s1+"."+s2+"."+s3;
    }

    public static String fill(String s){
        int fillNum = 8-s.length();
        for(int i = 0; i < fillNum; i++){
            s = "0"+s;
        }
        return s;
    }

    public static void priest(Device device, String path){
        BufferedWriter bufferedWriter = null;
        try {
            File file = new File(path + "/" + device.getMac() + ".txt");
            bufferedWriter = new BufferedWriter(new FileWriter(file));
            bufferedWriter.write(objectMapper.writeValueAsString(device));
        }catch (Exception e){

        }finally {
            try {
                bufferedWriter.close();
            }catch (Exception e){}
        }
    }

    public static <T> List<T> read(String city, String type, Class<T> tClass){
        BufferedReader bufferedReader = null;
        List<T> list = new ArrayList<>();
        try{
            File now = new File("");
            String path = now.getAbsolutePath()+"\\Game\\src\\main\\resources\\"+city+"\\"+type;
            File file = new File(path);
            if(file.isDirectory()){
                File[] files = file.listFiles();
                for (File file1 : files) {
                    bufferedReader = new BufferedReader(new FileReader(file1));
                    String s = bufferedReader.readLine();
                    T t = objectMapper.readValue(s, tClass);
                    list.add(t);
                    bufferedReader.close();
                }
            }else{
                return null;
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            try {
                bufferedReader.close();
            } catch (IOException e) {

            }
        }
        return list;
    }

    public static void initDevice(List<Device> devices, String city, String type){
        File file = new File("");
        String path = file.getAbsolutePath()+"\\Game\\src\\main\\resources\\"+city+"\\"+type;
        for (Device device : devices) {
            priest(device,path);
        }
    }
}
