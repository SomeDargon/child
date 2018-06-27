package com.child.json.customer;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class CustomerShareCommentReplyJson {
    @ApiModelProperty(value = "回复人Id")
    private Long replyCustomerId;
    @ApiModelProperty(value = "回复人姓名")
    private String replyCustomerName;
    @ApiModelProperty(value = "回复人头像")
    private String replyCustomerIcon;


}
