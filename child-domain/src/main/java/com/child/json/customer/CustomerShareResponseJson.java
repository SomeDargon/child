package com.child.json.customer;

import com.child.json.image.NorImageJson;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class CustomerShareResponseJson {

    private Long id;

    @ApiModelProperty(value = "标题")
    private String title;
    @ApiModelProperty(value = "名字")
    private String customerName;
    @ApiModelProperty(value = "发布人id")
    private Long customerId;
    @ApiModelProperty(value = "头像")
    private String customerIcon;
    @ApiModelProperty(value = "评论数量")
    private Integer commentCount;
    @ApiModelProperty(value = "内容")
    private String content;
    @ApiModelProperty(value = "添加时间")
    private Date addTime;
    @ApiModelProperty(value = "所有图片")
    private List<NorImageJson> imageJsonList;



}
