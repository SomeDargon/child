package com.child.json.customer;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class CustomerResponseJson {

    @ApiModelProperty(value = "authToken")
    private String authToken;

    private Long id;

}
