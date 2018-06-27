package com.child.json.doctor;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

@Data
public class DoctorChatCustomerResponseJson {


    @ApiModelProperty(value = "医生Id")
    private Long doctorId;
    @ApiModelProperty(value = "医生名字")
    private String doctorName;
    @ApiModelProperty(value = "医生头像")
    private String doctorIcon;
    @ApiModelProperty(value = "宝宝id")
    private Long childrenId;
    @ApiModelProperty(value = "宝宝名字")
    private String childrenName;
    @ApiModelProperty(value = "宝宝头像")
    private String childrenIcon;
    @ApiModelProperty(value = "客服id")
    private Long adminId;
    @ApiModelProperty(value = "客服名字")
    private String adminName;
    @ApiModelProperty(value = "客服头像")
    private String adminIcon;
    @ApiModelProperty(value = "内容")
    private String content;
    @ApiModelProperty(value = "添加时间")
    private Date addTime;
    @ApiModelProperty(value = "用户id")
    private Long customerId;
    @ApiModelProperty(value = "图片")
    private String pic;
    @ApiModelProperty(value = "1 客服 2 医生")
    private Integer userType;



}
