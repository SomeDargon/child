package com.child.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * Created by somedragon on 2018/2/9.
 */
@Data
public class ReceivingAddressDTO {

    private Long id;

    private String address;

    private Integer status;

    @ApiModelProperty(value = "用户名")
    private String userName;

    @ApiModelProperty(value = "用户电话")
    private String userPhone;

    private String company;
}
