package com.child.json.customer;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.Column;

@Data
public class CustomerShareCommentJson {

    private Long id;
    @ApiModelProperty(value = "共享之家id")
    private Long customerShareId;
    @ApiModelProperty(value = "内容")
    private String content;
    @ApiModelProperty(value = "回复人id")
    private Long replyCustomerId;


}
