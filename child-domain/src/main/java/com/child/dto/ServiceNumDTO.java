package com.child.dto;

import lombok.Data;

/**
 * Created by somedragon on 2018/3/22.
 */
@Data
public class ServiceNumDTO {
    private Integer phoneNum;
    private Double phonePrice;
    private Integer videoNum;
    private Double videoPrice;
    private Integer onlineNum;
    private Double onlinePrice;
    private Double total;
}
