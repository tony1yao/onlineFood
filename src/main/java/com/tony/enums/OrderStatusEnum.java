package com.tony.enums;

import lombok.Data;
import lombok.Getter;

@Getter
public enum OrderStatusEnum {
    NEW(0,"New order"),
    FINISHED(1,"Order completed"),
    CANCELLED(2,"Order cancelled")
    ;
    private  int code;
    private  String message;

    OrderStatusEnum(Integer code, String message){
        this.code = code;
        this.message = message;
    }
}
