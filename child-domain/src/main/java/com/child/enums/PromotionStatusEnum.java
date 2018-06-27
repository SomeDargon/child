package com.child.enums;

import lombok.Getter;

/**
 * Created by somedragon on 2018/2/9.
 */
@Getter
public enum PromotionStatusEnum implements CodeEnum {

    PROMOTION(0, "优惠"),
    NOT_PROMOTION(1, "不优惠");
    private Integer code;

    private String message;

    PromotionStatusEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }
}
