package com.child.json.doctor;

import com.child.json.image.NorImageJson;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class PatientEducationResponseJson {

    private Long id;
    @ApiModelProperty(value = "标题")
    private String title;
    @ApiModelProperty(value = "发布人")
    private String name;
    @ApiModelProperty(value = "提交时间")
    private Date addTime;
    @ApiModelProperty(value = "状态")
    private Integer status;

}
