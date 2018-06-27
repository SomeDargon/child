package com.child.json.admin;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

@Data
public class AdminLoginResponse {
	private Long id;
	@ApiModelProperty(value = "authToken")
	private String authToken;
	@ApiModelProperty(value = "账号")
	private String account;
	@ApiModelProperty(value = "名字")
	private String name;
	@ApiModelProperty(value = "头像")
	private String icon;
	@ApiModelProperty(value = "管理员类型  0.管理员 1.客服")
	private Integer adminType;
	@ApiModelProperty(value = "状态 1 启用   2 禁用 ")
	private Integer status;
	@ApiModelProperty(value = "添加时间")
	private Date addTime;


}
