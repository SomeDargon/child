package com.child.form;

import com.child.json.image.NorImageJson;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * Created by somedragon on 2018/3/30.
 */
@Data
public class CustomerShareResponseFrom {
    private Long id;
    @ApiModelProperty(value = "标题")
    private String title;
    @ApiModelProperty(value = "内容")
    private String content;
    @ApiModelProperty(value = "所有图片")
    private List<Long> imageJsonList;
}
