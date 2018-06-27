package com.child.json.admin;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class AdminLoginUser {
	@ApiModelProperty(value = "账号")
	private String account;
	@ApiModelProperty(value = "密码")
	private String password;
}
