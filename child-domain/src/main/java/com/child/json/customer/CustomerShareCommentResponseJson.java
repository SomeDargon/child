package com.child.json.customer;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class CustomerShareCommentResponseJson {

    @ApiModelProperty(value = "内容")
    private String content;
    @ApiModelProperty(value = "添加时间")
    private Date addTime;
    @ApiModelProperty(value = "评论人Id")
    private Long customerId;
    @ApiModelProperty(value = "评论人名字")
    private String customerName;
    @ApiModelProperty(value = "评论人头像")
    private String customerIcon;
    @ApiModelProperty(value = "回复人Id")
    private Long replyCustomerId;
    @ApiModelProperty(value = "回复人姓名")
    private String replyCustomerName;
    @ApiModelProperty(value = "回复人头像")
    private String replyCustomerIcon;

}
