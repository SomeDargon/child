package com.child.json.doctor;

import com.child.json.image.NorImageJson;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import springfox.documentation.annotations.ApiIgnore;

import java.util.Date;
import java.util.List;

@Data
public class DoctorConsultResponseJson {

    private Long id;
    @ApiModelProperty(value = "医生id")
    private Long doctorId;
    @ApiModelProperty(value = "医生姓名")
    private String name;

    @ApiModelProperty(value = "医生电话号码")
    private String phone;

    @ApiModelProperty(value = "医生职位")
    private String position;

    @ApiModelProperty(value = "医生科室")
    private String department;

    @ApiModelProperty(value = "医生头像")
    private String icon;

    @ApiModelProperty(value = "医院名称")
    private String servingHospital;

    @ApiModelProperty(value = "医院地址")
    private String hospitalAddress;

    @ApiModelProperty(value = "宝宝id")
    private Long childrenId;

    @ApiModelProperty(value = "宝宝姓名")
    private String childrenName;

    @ApiModelProperty(value = "宝宝年龄")
    private String age;

    @ApiModelProperty(value = "咨询类型 1.图文咨询 2.电话咨询 3.网络诊室 4.预约门诊")
    private Integer type;

    @ApiModelProperty(value = "联系电话")
    private String contactPhone;

    @ApiModelProperty(value = "添加时间")
    private Date addTime;

    @ApiModelProperty(value = "预约时间")
    private String time;
    @ApiModelProperty(value = "价格")
    private String price;

    private Long doctorBespeakTimeId;

    @ApiModelProperty(value = "状态 1. 正在进行 2. 已完成")
    private Integer status;
    @ApiModelProperty(value = "特殊病史")
    private String specialHistory;
    @ApiModelProperty(value = "过敏史")
    private String allergyHistory;
    @ApiModelProperty(value = "预产日期")
    private String expectedDate;
    @ApiModelProperty(value = "主要症状")
    private String problemContent;
    @ApiModelProperty(value = "诊断")
    private String diagnosis;
    @ApiModelProperty(value = "用药记录")
    private String medicationRecord;
    @ApiModelProperty(value = "检查结果")
    private String checkRecord;
    @ApiModelProperty(value = "医院名字")
    private String hospitalName;
    @ApiModelProperty(value = "就诊日期")
    private Date visitTime;
    @ApiModelProperty(value = "用药图片")
    private List<NorImageJson> medicationRecordJsonList;
    @ApiModelProperty(value = "检查图片")
    private List<NorImageJson> CheckRecordJsonList;


    public DoctorConsultResponseJson() {
    }

    public DoctorConsultResponseJson(Long id, Long childrenId,Long doctorId, String name, String phone, String position, String department, String icon, String servingHospital, String hospitalAddress, Integer type, String contactPhone, Date addTime, Integer status,Long doctorBespeakTimeId) {
        this.id = id;
        this.childrenId = childrenId;
        this.doctorId = doctorId;
        this.name = name;
        this.phone = phone;
        this.position = position;
        this.department = department;
        this.icon = icon;
        this.servingHospital = servingHospital;
        this.hospitalAddress = hospitalAddress;
        this.type = type;
        this.contactPhone = contactPhone;
        this.addTime = addTime;
        this.status = status;
        this.doctorBespeakTimeId = doctorBespeakTimeId;
    }
}
