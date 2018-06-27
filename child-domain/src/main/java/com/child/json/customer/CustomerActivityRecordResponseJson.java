package com.child.json.customer;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class CustomerActivityRecordResponseJson {

    private Long id;
    @ApiModelProperty(value = "用户id")
    private Long customerId;
    @ApiModelProperty(value = "用户姓名")
    private String customerName;
    @ApiModelProperty(value = "用户头像")
    private String icon;
    @ApiModelProperty(value = "联系电话")
    private String contactPhone;
    @ApiModelProperty(value = "报名人姓名")
    private String name;
    @ApiModelProperty(value = "人数")
    private String num;
    @ApiModelProperty(value = "活动Id")
    private Long activityId;



}
