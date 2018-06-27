package com.child.json.announcement;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

@Data
public class AnnouncementJson {

    private Long id;
    //类型
    @ApiModelProperty(value = "类型  1.最新活动 2.活动风采 3.共享之家")
    private Integer type;
    //标题
    @ApiModelProperty(value = "标题")
    private String title;
    //内容
    @ApiModelProperty(value = "内容")
    private String content;
    @ApiModelProperty(value = "简述")
    private String sketch;
    @ApiModelProperty(value = "名额")
    private Long number;
    @ApiModelProperty(value = "活动时间")
    private String activityTime;


}
