package com.child.form;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

/**
 * Created by somedragon on 2018/2/22.
 */
@Data
public class ExpressForm {

    @ApiModelProperty(value = "邮件编号")
    private String expressNo;

    @ApiModelProperty(value = "订单编号")
    private String orderId;

    @ApiModelProperty(value = "公司")
    private String company;


}
