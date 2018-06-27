package com.child.form;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * Created by somedragon on 2018/2/11.
 */
@Data
public class ProductCategoryForm {

    @ApiModelProperty(value = "类目id", hidden = true)
    private Long id;

    @ApiModelProperty(value = "类目名称")
    private String categoryName;
}
