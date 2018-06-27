package com.child.form;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * Created by somedragon on 2018/2/9.
 */
@Data
public class ProductForm {

    @ApiModelProperty(value = "id", hidden = true)
    private Long id;

    @ApiModelProperty(value = "名字")
    private String productName;

    @ApiModelProperty(value = "单价")
    private BigDecimal price;

    @ApiModelProperty(value = "品牌")
    private String brand;

    @ApiModelProperty(value = "库存")
    private Integer productStock;

    @ApiModelProperty(value = "描述")
    private String productDescription;

    @ApiModelProperty(value = "小图")
    private Long productIcon;

    @ApiModelProperty(value = "类目编号")
    private Long categoryType;

    @ApiModelProperty(value = "创建时间", hidden = true)
    private Date createTime;

    @ApiModelProperty(value = "图片列表")
    private String images;

    @ApiModelProperty(value = "现价", notes = "如果是，priceOr不能为空")
    private BigDecimal priceOr;

    @ApiModelProperty(value = "是否热销0表示热卖1表示不热卖")
    private int hot;

    @ApiModelProperty(value = "是否新品")
    private int isNew;

    @ApiModelProperty(value = "是否优惠")
    private int promotion;

}
