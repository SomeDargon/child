package com.child.form;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * Created by somedragon on 2018/2/9.
 */
@Data
public class ReceivingAddressFrom {

    @ApiModelProperty(value = "地址")
    private String address;

    @ApiModelProperty(value = "用户id", hidden = true)
    private Long userId;

    @ApiModelProperty(value = "用户名")
    private String userName;

    @ApiModelProperty(value = "用户电话")
    private String userPhone;
}
