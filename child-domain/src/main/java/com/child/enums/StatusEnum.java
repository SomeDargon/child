package com.child.enums;

import lombok.Getter;

/**
 * Created by somedragon on 2018/3/27.
 */
@Getter
public enum StatusEnum implements CodeEnum{
    DEFAULT(0, "未审核"),
    PASS(1, "通过"),
    NOT_PASS(2, "审核不通过"),

    ;

    private Integer code;

    private String message;

    StatusEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }
}
