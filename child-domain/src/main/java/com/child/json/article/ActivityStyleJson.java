package com.child.json.article;


import com.child.json.image.NorImageJson;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.Column;
import java.util.Date;
import java.util.List;

@Data
public class ActivityStyleJson {

    private Long id;

    @ApiModelProperty(value = "标题")
    private String title;
    @ApiModelProperty(value = "内容")
    private String content;
    @ApiModelProperty(value = "活动开始时间")
    private String startTime;
    @ApiModelProperty(value = "活动结束时间")
    private String endTime;
    @ApiModelProperty(value = "简述")
    private String sketch;
    @ApiModelProperty(value = "人数")
    private Integer num;
    @ApiModelProperty(value = "省id")
    private Long provinceId;
    @ApiModelProperty(value = "市id")
    private Long cityId;
    @ApiModelProperty(value = "剩余名额")
    private Integer surplusNum;
    @ApiModelProperty(value = "报名名额")
    private Integer reportNum;
    @ApiModelProperty(value = "上传活动封面")
    private List<Long> coverImageJsonList;
    @ApiModelProperty(value = "上传活动结束图片")
    private List<Long> imageJsonList;


}
