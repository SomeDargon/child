package com.child.enums;

import lombok.Getter;

/**
 * Created by somedragon on 2018/2/9.
 */
@Getter
public enum  NewStatusEnum implements CodeEnum {

    NEW(0, "新产品"),
    NOT_NEW(1, "不是新产品"),
            ;

    private Integer code;

    private String message;

    NewStatusEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }
}
