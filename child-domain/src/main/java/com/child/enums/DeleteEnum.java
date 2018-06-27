package com.child.enums;

import lombok.Getter;

/**
 * Created by somedragon on 2018/2/22.
 */
@Getter
public enum  DeleteEnum implements CodeEnum {
    DEFAULT(0, "未删除"),
    DELETE(1, "删除")
            ;
    private Integer code;

    private String message;

    DeleteEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }
}

