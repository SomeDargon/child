package com.child.enums;

import lombok.Getter;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * Created by somedragon on 2018/3/26.
 */
@Getter
public enum RoleEnum implements CodeEnum{
    TOURIST(0, "普通用户"),
    DOCTOR(2, "医生"),
    ASSISTANT(1, "小助手"),

    ;

    private Integer code;

    private String message;

    RoleEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }
}
