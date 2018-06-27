package com.child.form;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * Created by somedragon on 2018/3/26.
 */
@Data
public class DoctorForm {
    @ApiModelProperty(value = "编号")
    private Long id;
    @ApiModelProperty(value = "状态")
    private String type;
    @ApiModelProperty(value = "在线")
    private Integer online;
}
