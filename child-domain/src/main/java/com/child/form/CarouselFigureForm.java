package com.child.form;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * Created by somedragon on 2018/2/9.
 */
@Data
public class CarouselFigureForm {

    @ApiModelProperty(value = "编号")
    private Long id;

    @ApiModelProperty(value = "图片id")
    private Long imageId;
}
