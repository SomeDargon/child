package com.child.enums;

import lombok.Getter;

/**
 * Created by somedragon on 2018/2/9.
 */
@Getter
public enum HotStatusEnum implements CodeEnum {
    HOT(0, "热销"),
    NOT_HOT(1, "不热销")
    ;
    private Integer code;

    private String message;

    HotStatusEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }
}
