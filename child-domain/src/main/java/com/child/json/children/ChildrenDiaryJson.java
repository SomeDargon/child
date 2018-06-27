package com.child.json.children;

import com.child.json.image.NorImageJson;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class ChildrenDiaryJson {

    private Long id;
    @ApiModelProperty(value = "孩子id")
    private Long childrenId;
    @ApiModelProperty(value = "标题")
    private String title;
    @ApiModelProperty(value = "内容")
    private String content;
    @ApiModelProperty(value = "所在位置")
    private String address;
    @ApiModelProperty(value = "所有图片")
    private List<NorImageJson> imageJsonList;



}
