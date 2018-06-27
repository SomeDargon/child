package com.child.form;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * Created by somedragon on 2018/2/9.
 */
@Data
public class CommentForm {

    @ApiModelProperty(value = "商品编号")
    private Long productId;

    @ApiModelProperty(value = "评论内容")
    private String content;
}
