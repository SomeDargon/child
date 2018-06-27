package com.child.json.doctor;

import com.child.json.image.NorImageJson;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class DoctorConsultDetailJson {

    private Long id;
    @ApiModelProperty(value = "医生id")
    private Long doctorId;
    @ApiModelProperty(value = "宝宝id")
    private Long childrenId;
    @ApiModelProperty(value = "宝宝名字")
    private String childrenName;
    @ApiModelProperty(value = "预约时间id")
    private Long doctorBespeakTimeId;
    @ApiModelProperty(value = "咨询类型 1.图文咨询 2.电话咨询 3.网络诊室 4.预约门诊")
    private Integer type;
    @ApiModelProperty(value = "医生名字")
    private String doctorName;
    @ApiModelProperty(value = "咨询内容")
    private String problemContent;
    @ApiModelProperty(value = "门诊时间")
    private String time;
    @ApiModelProperty(value = "添加时间")
    private Date addTime;
    @ApiModelProperty(value = "就诊时间")
    private Date visitTime;
    @ApiModelProperty(value = "状态 1. 正在进行 2. 已完成")
    private Integer status;
    @ApiModelProperty(value = "1. 系统生成 2.用户手动添加")
    private Integer sysType;
    @ApiModelProperty(value = "电话号码")
    private String phone;
    @ApiModelProperty(value = "最后一次聊天时间")
    private Date endTime;
    @ApiModelProperty(value = "第一次提问问题")
    private String questionName;
    /** 目前用户 就只有3个 用户， 客户， 医生 所以做了三个 判断， 如果用户多了需要加一张临时表*/
    @ApiModelProperty(value = "用户只读未读状态")
    private Integer userStatus;




}
