package com.tony.enums;


import lombok.Getter;

@Getter
public enum ProductStatusEnum {
    IN_STOCK(0,"In stock"),
    OUTOF_STOCK(1,"Out of stock")
    ;
    private  Integer code;
    private  String message;

    ProductStatusEnum(Integer code, String message){
        this.code = code;
        this.message = message;
    }
}
