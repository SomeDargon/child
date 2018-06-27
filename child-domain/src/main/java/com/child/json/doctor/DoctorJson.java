package com.child.json.doctor;

import com.child.json.image.NorImageJson;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
public class DoctorJson {

    private Long id;

    @ApiModelProperty(value = "姓名")
    private String name;

    @ApiModelProperty(value = "电话号码")
    private String phone;

    @ApiModelProperty(value = "简介")
    private String remarks;

    @ApiModelProperty(value = "职位")
    private String position;

    @ApiModelProperty(value = "科室")
    private String department;

    @ApiModelProperty(value = "头像")
    private String icon;

    @ApiModelProperty(value = "擅长")
    private String goodAt;

    @ApiModelProperty(value = "价格")
    private String price;

    @ApiModelProperty(value = "医生类型 1.图文咨询 2.电话咨询 3.网络诊室 4.预约门诊 可以多选以逗号分隔")
    private String type;

    @ApiModelProperty(value = "医院名称")
    private String servingHospital;

    @ApiModelProperty(value = "医院地址")
    private String hospitalAddress;

    @ApiModelProperty(value = "省id")
    private Long provinceId ;
    @ApiModelProperty(value = "省名称")
    private String provinceName ;
    @ApiModelProperty(value = "是否在线 1.在线 2.不在线")
    private Integer onLine;
    @ApiModelProperty(value = "头像上传数据,文件类型。如果没有修改就不传数据")
    private List<NorImageJson> imageJsonList;


}
