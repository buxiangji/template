package com.bxj.shop.wares;

import lombok.Data;

@Data
public class Wares {
    private String uuid;
    private String name;
    private String type;
    private double price;
    private double weight;
}
