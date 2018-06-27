package com.child.form;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * Created by somedragon on 2018/2/9.
 */
@Data
public class OrderForm {

    @ApiModelProperty(value = "买家姓名")
    private String name;

    @ApiModelProperty(value = "买家电话")
    private String phone;

    @ApiModelProperty(value = "买家地址")
    private String address;

    @ApiModelProperty(value = "openId", hidden = true)
    private String openid;

    @ApiModelProperty(value = "购物车", hidden = true, example = "[{id:1,productQuantity:3}]")
    private String items;
}
