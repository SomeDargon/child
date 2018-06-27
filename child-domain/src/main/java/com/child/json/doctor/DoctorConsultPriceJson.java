package com.child.json.doctor;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class DoctorConsultPriceJson {

    private Long id;

    @ApiModelProperty(value = "医生姓名")
    private String doctorName;

    @ApiModelProperty(value = "医生id")
    private Long doctorId;

    @ApiModelProperty(value = "类型")
    private String type;

    @ApiModelProperty(value = "价格")
    private String price;

}
