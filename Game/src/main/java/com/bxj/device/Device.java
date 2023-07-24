package com.bxj.device;

import lombok.Data;

/**
 * @author buxiangji
 * @makedate 2023/7/20 14:28
 */
@Data
public abstract class Device {
    private String mac;
    private String ip;
    private String mask;
}
