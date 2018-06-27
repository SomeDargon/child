package com.child.json.doctor;

import com.child.json.image.NorImageJson;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.Column;
import java.util.Date;
import java.util.List;

@Data
public class PatientEducationJson {

    private Long id;
    @ApiModelProperty(value = "类型 1 原创 2 转载")
    private Integer type;
    @ApiModelProperty(value = "来源")
    private String resource;
    @ApiModelProperty(value = "标题")
    private String title;
    @ApiModelProperty(value = "链接地址")
    private String linkAddress;
    @ApiModelProperty(value = "简述")
    private String sketch;
    @ApiModelProperty(value = "详情")
    private String details;
    @ApiModelProperty(value = "0:未审核，1:审核通过，2：驳回")
    private Integer status;
    @ApiModelProperty(value = "驳回信息")
    private String notPassRemarks;
    @ApiModelProperty(value = "所有图片")
    private List<Long> imageList;


}
