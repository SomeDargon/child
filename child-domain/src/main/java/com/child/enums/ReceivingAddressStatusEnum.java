package com.child.enums;

import lombok.Getter;

/**
 * Created by somedragon on 2018/2/9.
 */
@Getter
public enum ReceivingAddressStatusEnum implements CodeEnum {

    NOT_DEFAULT(0, "常用地址"),
    DEFAULT(1, "默认地址");
    private Integer code;

    private String message;

    ReceivingAddressStatusEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }
}
