package com.child.json.doctor;

import com.child.json.image.NorImageJson;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;


@Data
public class DoctorChatCustomerJson {

    @ApiModelProperty(value = "问题Id")
    private Long doctorConsultId;
    @ApiModelProperty(value = "咨询类型")
    private Integer type;
    @ApiModelProperty(value = "医生Id")
    private Long doctorId;
    @ApiModelProperty(value = "用户id")
    private Long customerId;
    @ApiModelProperty(value = "客服id")
    private Long adminId;
    @ApiModelProperty(value = "1 客服 2 医生")
    private Integer userType;
    @ApiModelProperty(value = "咨询内容")
    private String content;
    @ApiModelProperty(value = "图片")
    private List<NorImageJson> imageJsonList;


}
