package com.child.json.doctor;

import com.child.json.image.NorImageJson;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Lob;
import java.util.Date;
import java.util.List;

@Data
public class DoctorConsultArchivesJson {

    private Long id;

    private Long childrenId;
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



}
