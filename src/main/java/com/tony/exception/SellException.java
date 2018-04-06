package com.tony.exception;

import com.tony.enums.ExceptionEnum;

public class SellException extends RuntimeException {
    private int code;


    public SellException(ExceptionEnum exceptionEnum){
        super(exceptionEnum.getMsg());
        this.code = exceptionEnum.getCode();
    }

    public SellException(Integer code, String message){
        super(message);
        this.code = code;
    }

}
