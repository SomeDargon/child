package com.child.json.doctor;

import com.child.json.image.NorImageJson;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;


@Data
public class DoctorConsultHistoryJson {

    private Long doctorConsultId;
    @ApiModelProperty(value = "症状描述")
    private String problemContent;
    @ApiModelProperty(value = "其他症状")
    private String otherSymptom;
    @ApiModelProperty(value = "病历资料图片")
    private List<NorImageJson> imageJsonList;



}
