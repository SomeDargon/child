package com.child.json.article;

import com.child.json.image.NorImageJson;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class LatestActivityResponseJson {

    private Long id;
    @ApiModelProperty(value = "标题")
    private String title;
    @ApiModelProperty(value = "内容")
    private String content;
    @ApiModelProperty(value = "发布人")
    private String publishName;
    @ApiModelProperty(value = "发布人id")
    private Long publishId;
    @ApiModelProperty(value = "简述")
    private String sketch;
    @ApiModelProperty(value = "封面")
    private String cover;
    @ApiModelProperty(value = "添加时间")
    private Date addTime;
    @ApiModelProperty(value = "名额")
    private Integer num;
    @ApiModelProperty(value = "省id")
    private Long provinceId;
    @ApiModelProperty(value = "市id")
    private Long cityId;
    @ApiModelProperty(value = "剩余名额")
    private Integer surplusNum;
    @ApiModelProperty(value = "报名名额")
    private Integer reportNum;
    @ApiModelProperty(value = " 1 正在进行  2 已结束")
    private Integer status;
    @ApiModelProperty(value = "活动开始时间")
    private String startTime;
    @ApiModelProperty(value = "活动结束时间")
    private String endTime;
    @ApiModelProperty(value = "活动结束图片")
    private List<NorImageJson> imageJsonList;


}
