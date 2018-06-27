package com.child.exception;

import com.child.enums.ResultEnum;
import lombok.Getter;

/**
 * Created by somedragon on 2018/2/9.
 */
@Getter
public class childException extends RuntimeException{

    private Integer code;

    public childException(ResultEnum resultEnum){
        super(resultEnum.getMessage());
        this.code = resultEnum.getCode();
    }

    public childException(Integer code, String message){
        super(message);
        this.code = code;
    }
}
