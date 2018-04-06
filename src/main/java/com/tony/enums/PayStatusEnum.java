package com.tony.enums;

import lombok.Getter;

@Getter
public enum PayStatusEnum {
    UNPAID(0,"Not paid yet, waiting for payment"),
    SUCCESS(1,"Payment is done."),
    ;
    private  int code;
    private  String message;

    PayStatusEnum(Integer code, String message){
        this.code = code;
        this.message = message;
    }
}
