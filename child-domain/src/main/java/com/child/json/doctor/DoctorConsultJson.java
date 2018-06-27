package com.child.json.doctor;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.Column;
import java.util.Date;

@Data
public class DoctorConsultJson {

    private Long id;
    @ApiModelProperty(value = "医生id")
    private Long doctorId;
    @ApiModelProperty(value = "宝宝id")
    private Long childrenId;
    @ApiModelProperty(value = "预约时间id")
    private Long doctorBespeakTimeId;
    @ApiModelProperty(value = "咨询类型 1.图文咨询 2.电话咨询 3.网络诊室 4.预约门诊")
    private Integer type;
    @ApiModelProperty(value = "联系电话")
    private String contactPhone;
    @ApiModelProperty(value = "咨询内容")
    private String problemContent;

}
