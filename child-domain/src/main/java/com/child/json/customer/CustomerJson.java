package com.child.json.customer;

import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiParam;
import lombok.Data;

import java.util.Date;

/**
 * Created by somedragon on 2018/1/23.
 */
@Data
public class CustomerJson {

    private Long id;

    @ApiModelProperty(value = "真实姓名")
    private String realName;

    @ApiModelProperty(value = "出生年月")
    private Date createDate;

    @ApiModelProperty(value = "联系方式")
    private String phone;

    @ApiModelProperty(value = "家庭住址")
    private String address;

    @ApiModelProperty(value = "紧急联系人")
    private String urgentPhone;

    @ApiModelProperty(value = "身份证")
    private String idNumber;
    @ApiModelProperty(value = "头像")
    private String icon;

}
