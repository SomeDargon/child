package com.child.json.doctor;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.Column;

@Data
public class DoctorBespeakTimeJson {

    private Long id;
    @ApiModelProperty(value = "医生id")
    private Long doctorId;
    @ApiModelProperty(value = "时间")
    private String time;
    @ApiModelProperty(value = "数量")
    private Integer num;


}
