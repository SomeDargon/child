package com.child.json.children;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;
@Data
public class ChildrenGrowRecordResponseJson {

    private Long id;

    private Long childrenId;
    @ApiModelProperty(value = "身高")
    private String height;
    @ApiModelProperty(value = "体重")
    private String weight;
    @ApiModelProperty(value = "头围")
    private String headCircumference;
    @ApiModelProperty(value = "添加时间")
    private Date addTime;
    @ApiModelProperty(value = "测量时间")
    private String measurementTime;
    @ApiModelProperty(value = "年龄")
    private Integer age;



}
