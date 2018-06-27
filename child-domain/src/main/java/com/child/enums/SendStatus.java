package com.child.enums;

import lombok.Getter;

/**
 * Created by somedragon on 2018/2/22.
 */
@Getter
public enum  SendStatus implements CodeEnum {

    WAIT(0, "未发货"),
    SUCCESS(1, "已发货"),

            ;

    private Integer code;

    private String message;

    SendStatus(Integer code, String message) {
        this.code = code;
        this.message = message;
    }
}
