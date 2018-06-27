package com.child.json.customer;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.Column;

@Data
public class CustomerActivityRecordJson {
    private Long id;
    @ApiModelProperty(value = "活动Id")
    private Long activityId;
    @ApiModelProperty(value = "联系电话")
    private String contactPhone;
    @ApiModelProperty(value = "报名人姓名")
    private String name;
    @ApiModelProperty(value = "人数")
    private Integer num;

}
