package com.child.json.admin;

import com.child.json.image.NorImageJson;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.Column;
import java.util.Date;
import java.util.List;

@Data
public class AdminJson {

    private Long id;
    @ApiModelProperty(value = "账号")
    private String account;
    @ApiModelProperty(value = "名字")
    private String name;
    @ApiModelProperty(value = "密码")
    private String password;
    @ApiModelProperty(value = "角色 0 管理员  1 客服")
    private Integer adminType;
    @ApiModelProperty(value = "图片")
    private List<NorImageJson> imageJsonList;



}
